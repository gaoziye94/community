package life.zwp.community.service.serviceimpl;

import life.zwp.community.mapper.UserMapper;
import life.zwp.community.model.User;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }
}
