package life.zwp.community.mapper;

import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("select * from question order by gmt_modified desc")
    List<Question> findAll();

    /**
     * 查询所有记录数
     * @return
     */
//    @Select("select count(1) from question where creator = #{creator}")
    Integer count( @Param("creator") Integer creator);

    /**
     * 查询某个人的所有问题
     * @param start  开始条
     * @param size   每页几条
     * @param id
     * @return
     */
    @Select("select * from question  where creator = #{id} order by question.gmt_modified desc limit #{start},#{size}")
    List<Question> findQuestionByUserId(Integer start, Integer size, Integer id);

    /**
     * 根据问题id查找问题，回显
     * @param id
     * @return
     */
    @Select("select * from question  where id = #{id}")
    Question findQuestionById(Integer id);

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
    Map<Object, Object> findQuestionMapById(Integer id);
}
