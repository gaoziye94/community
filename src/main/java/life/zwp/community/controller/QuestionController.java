package life.zwp.community.controller;

import life.zwp.community.dto.Message;
import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Comment;
import life.zwp.community.model.Question;
import life.zwp.community.model.User;
import life.zwp.community.service.NotificationService;
import life.zwp.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller("questionController")
@RequestMapping("/question")
public class QuestionController extends BaseController{

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;
    /**
     * 问题详情页
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String detail( HttpServletRequest request, HttpServletResponse response,
                          Model model,@PathVariable(name = "id") Long id) {

        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
//        model.addAttribute("name",id);
//        浏览数增加1
        Question question = new Question();
        question.setId(questionDTO.getQuestion().getId());
//        多个人同事访问会产生并发
        question.setViewCount(1);
        questionService.updateViewCount(question);
        //问题详情
        model.addAttribute("questionDTO",questionDTO);
//        加载评论列表,1级评论
        //1级评论
        Integer type = 1;
        List<Map<String,Object>> commentList =  questionService.commentList(id,type);
        //评论列表，1级
        model.addAttribute("commentList",commentList);
        //获取相关问题，根据tags
        String tags = questionDTO.getQuestion().getTags();
        List<Question> relatedQuestions =  questionService.relatedQuestion(id,tags);
        model.addAttribute("relatedQuestions",relatedQuestions);

        //我的未读消息
        unreadCount(request,model);
        return "question";
    }

    @PostMapping("/del")
    @ResponseBody
    public Message del(Long id){
        Question question = new Question();
        question.setId(id);
        int count  = questionService.deleteQuestion(question);
        return (count == 1) ? Message.MESSAGE_DEL_SUCCESS: Message.MESSAGE_DEL_ERROR;
    }

}
