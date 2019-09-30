package life.zwp.community.service.serviceimpl;

import life.zwp.community.exception.CustomizeErrorCode;
import life.zwp.community.exception.CustomizeException;
import life.zwp.community.mapper.CommentMapper;
import life.zwp.community.mapper.QuestionMapper;
import life.zwp.community.model.Comment;
import life.zwp.community.model.Question;
import life.zwp.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    @Transactional
    public int insert(Comment comment) {
        int count = 0;
        //判断问题是否存在
        if(comment.getParentId() == null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_QUESTION_NOT_FOUND);
        }
        //判断评论类型是否存在，或者错误
        if(comment.getType() == null || !Comment.CommentType.isExeist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_WRONG);
        }
        if(comment.getType() == Comment.CommentType.COMMENT.getType()){
            Long parentId = comment.getParentId();
            //评论。一级type =1
            Question question = questionMapper.findQuestionById(parentId);
            if(question ==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //增加一条评论进数据库
            count = commentMapper.insert(comment);
            //将该问题的评论数+1
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);
        } else if (comment.getType() == Comment.CommentType.REPLY.getType()){
            //回复.二级type =2
            Comment comment1 = commentMapper.selectById(comment.getParentId());
            if(comment1 == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //增加一条评论进数据库
            count = commentMapper.insert(comment);
            //
            comment1.setReplyCount(1L);
            commentMapper.update(comment1);
        }
        return  count;
    }

    @Override
    public int delete(Comment comment) {
        return commentMapper.delete(comment);
    }

    @Override
    public int update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public Comment selectById(Long id) {
        return commentMapper.selectById(id);
    }
}
