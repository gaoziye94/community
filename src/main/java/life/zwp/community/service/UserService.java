package life.zwp.community.service;

import life.zwp.community.model.User;

public interface UserService {

    /**
     * 增加用户
     * @param user
     */
    void insert(User user);

    /**
     * 根据token,获取用户
     * @param token
     * @return
     */
    User findByToken(String token);
}
