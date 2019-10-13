package life.zwp.community.mapper;

import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 问题-mapper
 */
@Mapper
@Component
public interface QuestionMapper {

    /**
     * 新增
     * @param question
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    void insert(Question question);

    /**
     * 查询所有
     */
    @Select("select * from question order by gmt_create desc")
    List<Question> findAll();

    /**
     * 查询所有记录数
     * @return
     * @param creator
     */
//    @Select("select count(1) from question where creator = #{creator}")
    Integer count( @Param("creator") Long creator);

    /**
     * 查询某个人的所有问题
     * @param start  开始条
     * @param size   每页几条
     * @param id
     * @return
     */
    @Select("select * from question  where creator = #{id} order by question.gmt_modified desc limit #{start},#{size}")
    List<Question> findQuestionByUserId(Integer start, Integer size, Long id);

    /**
     * 根据问题id查找问题，回显
     * @param id
     * @return
     */
    @Select("select * from question  where id = #{id}")
    Question findQuestionById(@Param("id")Long id);

    /**
     * 更新问题,根据id
     * @param question
     */
    void update(Question question);

    /**
     * 查询问题，并返回发布人
     * @param id
     * @return
     */
    Map<Object, Object> findQuestionMapById(Long id);

    /**
     * 每次访问问题，浏览数+1.
     * update question set view_count = view_count +1 where id = ?
     * @param question
     */
    void updateViewCount(Question question);

    /**
     * 评论完成，评论数+1
     * @param question
     */
    @Update("update question set comment_count = comment_count + #{commentCount} where id = #{id}")
    void incCommentCount(Question question);

    /**
     * 查询相关问题
     * @param id
     * @param tag
     * @return
     */
    @Select("select *  from question where tags REGEXP #{tag} and id !=#{id}")
    List<Question> relatedQuestion(@Param("id") Long id, @Param("tag")String tag);

    /**
     * 删除问题
     * @param question
     * @return
     */
    @Delete("delete from question where id = #{id}")
    int delete(Question question);
}
