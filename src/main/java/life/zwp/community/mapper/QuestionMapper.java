package life.zwp.community.mapper;

import life.zwp.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

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
    @Select("select count(1) from question")
    Integer count();
}
