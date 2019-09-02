package life.zwp.community.mapper;

import life.zwp.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,bio,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{bio},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select id,name,account_id,token,bio,gmt_create,gmt_modified from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
