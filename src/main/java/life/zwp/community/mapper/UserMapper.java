package life.zwp.community.mapper;

import life.zwp.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {


    /**
     * 新增
     * @param user
     */
    void insert(User user);

    /**
     * 查询用户，根据token
     * @param token
     * @return
     */
    User findByToken(String token);

    /**
     * 根据创建者id，查询这个用户
     * @param creator
     * @return
     */
    User findByCreator(Integer creator);

    /**
     * 根据创建者AccountId，查询这个用户
     * @param accountId
     * @return
     */
    User findByAccountId(String accountId);

    /**
     * 更新用户，根据accountId
     * @param user
     */
    void update(User user);

}
