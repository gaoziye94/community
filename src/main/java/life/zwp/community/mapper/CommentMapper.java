package life.zwp.community.mapper;

import life.zwp.community.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CommentMapper {
    /**
     * 增
     * @param comment
     */
    @Insert("insert into comment (parent_id,type,commentator,content,like_count,reply_count,gmt_create,gmt_modified) values (#{parentId},#{type},#{commentator},#{content},#{likeCount},#{replyCount},#{gmtCreate},#{gmtModified})")
    int insert(Comment comment);

    /**
     * 删
     * @param comment
     * @return
     */
    @Delete("delete from comment where id=#{id}")
    int delete(Comment comment);

    /**
     * 改
     */
    int update(Comment comment);

    /**
     * 查
     */
    @Select("select * from comment where id=#{id}")
    Comment selectById(Long id);

    /**
     * 根据id查找问题评论,1级评论
     * @param id
     * @param type
     * @return
     */
    @Select("SELECT c.id ,c.content,c.like_count,c.reply_count,c.gmt_modified,u.`name`,u.head_url from `comment` c LEFT JOIN `user` u on c.commentator = u.id  where c.parent_id = #{parentId} and c.type = #{type} ORDER BY c.gmt_create DESC")
    List<Map<String,Object>> commentList(@Param("parentId") Long id, @Param("type")Integer type);

    /**
     * 点赞，并返回对象
     * @param comment
     * @return
     */
    Comment likeComment(Comment comment);
}
