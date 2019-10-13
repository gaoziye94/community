package life.zwp.community.controller;

import life.zwp.community.dto.CommentDTO;
import life.zwp.community.dto.Message;
import life.zwp.community.exception.CustomizeErrorCode;
import life.zwp.community.exception.CustomizeException;
import life.zwp.community.model.Comment;
import life.zwp.community.model.User;
import life.zwp.community.service.CommentService;
import life.zwp.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 评论controller
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;
    /**
     * 新增评论
     * @param commentDTO
     * @param request
     * @return
     */
    @PostMapping("/insert")
    @ResponseBody
    private Message insertComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        // @RequestBody可以将前端返回的json格式数据转成实体类
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        User user = (User) request.getSession().getAttribute("user");
        if(user ==null){
            return Message.NO_LOGIN;
        }
        //判断评论是否为空，为空给出提示
        if(comment == null || !StringUtils.isNoneBlank(commentDTO.getContent())){
            return Message.commonError(CustomizeErrorCode.COMMENT_CONTENT_IS_BLANK.getCode(),CustomizeErrorCode.COMMENT_CONTENT_IS_BLANK.getMessage());
        }
        comment.setCommentator(user.getId());
//        comment.setCommentator(15L);
        comment.setLikeCount(0L);
        comment.setReplyCount(0L);
        comment.setGmtCreate(new Date().getTime());
        comment.setGmtModified(comment.getGmtCreate());
        int count = commentService.insert(comment);
        return (count == 1)?Message.commonSuccess():Message.commonError("10000","您的操作出现错误，请重试");
    }

    /**
     * 点赞评论
     * @param id
     * @return
     */
    @PostMapping("/like")
    @ResponseBody
    public Message like(Long id){
        if (id == null) {
            return Message.commonError(CustomizeErrorCode.COMMENT_QUESTION_NOT_FOUND.getCode(),CustomizeErrorCode.COMMENT_QUESTION_NOT_FOUND.getMessage()) ;
        }
        Comment comment = new Comment();
        comment.setId(id);
        comment.setLikeCount(1L);
        comment.setGmtModified(System.currentTimeMillis());
        //跟新数据，并返回这个对象
        int count = commentService.update(comment);
        Comment record = new Comment();
        if(count == 1){
            record = commentService.selectById(id);
        }
        return (count == 1)?Message.commonSuccess(record):Message.commonError("10000","您的操作出现错误，请重试");
    }

    /**
     * 根据id,查询评论的回复，二级评论
     * @param id
     * @return
     */
    @GetMapping("/reply")
    @ResponseBody
    public List<Map<String, Object>> listApply(Long id){
        //二级评论
        Integer type = 2;
        List<Map<String, Object>> replyList = questionService.commentList(id, type);
        return replyList;
    }
}
