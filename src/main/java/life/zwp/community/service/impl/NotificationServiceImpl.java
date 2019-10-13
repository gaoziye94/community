package life.zwp.community.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.exception.CustomizeErrorCode;
import life.zwp.community.exception.CustomizeException;
import life.zwp.community.mapper.NotificationMapper;
import life.zwp.community.model.Notification;
import life.zwp.community.model.User;
import life.zwp.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("notificationServiceImpl")
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public int insert(Notification notification) {
        int count = notificationMapper.insert(notification);
        return count;
    }

    @Override
    public int delete(Notification notification) {
        Notification record = notificationMapper.select(notification.getId());
        if(record == null){
            throw new CustomizeException(CustomizeErrorCode.NOTICE_IS_BLANK);
        }
        return notificationMapper.delete(record);
    }

    @Override
    public void update(Long id) {
        notificationMapper.update(id);
    }

    @Override
    public Notification select(Long id) {
        Notification notification = notificationMapper.select(id);
        return notification;
    }

    @Override
    public int MyNoticeCount(User user) {
        return 0;
    }

    @Override
    public PaginationDTO findNotificationList(Integer page, Integer size, Long userId) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //分页
       PageHelper.startPage(page,size);
       List<Map<String,Object>> notificationListMap =  notificationMapper.findNotificationListMap(userId);
        //最后一步,获取分页信息
        PageInfo info = new PageInfo<>(notificationListMap);
        paginationDTO.setContent(notificationListMap);
        paginationDTO.setPaginationDTO(new Long(info.getTotal()).intValue(),page,size);
        return paginationDTO;
    }

    @Override
    public Integer myCount(Long id) {
        //我的未读通知，status = 0
        Integer count = notificationMapper.count(id,0);
        return count;
    }

    @Override
    public Notification select(Long id, User user) {
        Notification notification = notificationMapper.select(id);
        if(!Objects.equals(notification.getReceiver(), user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTICE_FAIL);
        }
        return notification;
    }
}
