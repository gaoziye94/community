package life.zwp.community.service.impl;

import life.zwp.community.mapper.NotificationMapper;
import life.zwp.community.model.Comment;
import life.zwp.community.model.Notification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceImplTest {

    @Autowired
    private NotificationMapper notificationMapper;
    @Test
    public void insert() {
        Notification notification = new Notification();
        notification.setOuterId(12313L);
        notification.setNotifier(15L);
        notification.setReceiver(15L);
        notification.setType(1);
        notification.setStatus(0);
        notification.setGmt_create(System.currentTimeMillis());
        int count = notificationMapper.insert(notification);
        if(count == 1){
           System.err.println("新增成功");
        }
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void select() {
    }
}
