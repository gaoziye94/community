package life.zwp.community.service.serviceimpl;

import life.zwp.community.mapper.UserMapper;
import life.zwp.community.model.User;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceImpl")
@Transactional
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

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User findByAccountId(String accountId) {
        return userMapper.findByAccountId(accountId);
    }
}
