package life.zwp.community.service;

import life.zwp.community.model.Comment;

/**
 * 评论接口
 */
public interface CommentService {
    /**
     * 增
     * @param comment
     */
    int insert(Comment comment);
    /**
     * 删
     * @param comment
     */
    int delete(Comment comment);
    /**
     * 改
     * @param comment
     */
    int update(Comment comment);
    /**
     * 查
     * @param id
     */
    Comment selectById(Long id);
}
