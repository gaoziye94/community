package life.zwp.community.service;

import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.model.Notification;
import life.zwp.community.model.User;

/**
 * 消息通知 service
 */
public interface NotificationService {
    /**
     * 增
     * @param notification
     * @return
     */
    int insert(Notification notification);
    int delete(Notification notification);
    void update(Long id);
    Notification select(Long id);

    /**
     * 我的通知条数
     * @param user
     * @return
     */
    int MyNoticeCount(User user);

    /**
     * 通知我的消息
     *
     * @param page
     * @param size
     * @param user
     * @return
     */
    PaginationDTO findNotificationList(Integer page, Integer size, Long user);

    /**
     * 关于我的通知
     * @param id
     * @return
     */
    Integer myCount(Long id);

    /**
     *
     * @param id
     * @param user
     * @return
     */
    Notification select(Long id, User user);
}
