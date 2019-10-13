package life.zwp.community.service.impl;

import life.zwp.community.enums.NotificationStatusEnum;
import life.zwp.community.exception.CustomizeErrorCode;
import life.zwp.community.exception.CustomizeException;
import life.zwp.community.mapper.CommentMapper;
import life.zwp.community.mapper.NotificationMapper;
import life.zwp.community.mapper.QuestionMapper;
import life.zwp.community.model.Comment;
import life.zwp.community.model.Notification;
import life.zwp.community.model.Question;
import life.zwp.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationMapper notificationMapper;
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
        Question question;
        if(comment.getType().equals(Comment.CommentType.COMMENT.getType())){
            Long parentId = comment.getParentId();
            //评论。一级type =1
            question = questionMapper.findQuestionById(parentId);
            if(question ==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //增加一条评论进数据库
            count = commentMapper.insert(comment);
            //将该问题的评论数+1
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);

            //新增通知
            createNotifition(question.getId(), comment.getCommentator(),question.getCreator(),Comment.CommentType.COMMENT.getType(),NotificationStatusEnum.UNREAD.getType(),question.getId());
        } else if (comment.getType().equals(Comment.CommentType.REPLY.getType())){
            //回复.二级type =2
            Comment comment1 = commentMapper.selectById(comment.getParentId());
            if(comment1 == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //查找这个回复的所属的question
            question = questionMapper.findQuestionById(comment1.getParentId());
            //增加一条评论进数据库
            count = commentMapper.insert(comment);
            //
            comment1.setReplyCount(1L);
            commentMapper.update(comment1);
            //新增通知
            createNotifition(comment1.getId(), comment.getCommentator(),comment1.getCommentator(),Comment.CommentType.REPLY.getType(),NotificationStatusEnum.UNREAD.getType(),question.getId());
        }
        return  count;
    }

    /**
     * 新增通知
     * @param outerId
     * @param notifier
     * @param receiver
     * @param type
     * @param status
     */
    private void createNotifition(Long outerId, Long notifier,Long receiver,Integer type,Integer status,Long questionId) {
        Notification notification = new Notification();
        notification.setOuterId(outerId);
        notification.setNotifier(notifier);
        notification.setReceiver(receiver);
        notification.setType(type);
        notification.setStatus(status);
        notification.setGmt_create(System.currentTimeMillis());
        notification.setQuestionId(questionId);
        notificationMapper.insert(notification);
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
