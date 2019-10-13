package life.zwp.community.mapper;

import life.zwp.community.model.Notification;
import life.zwp.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface NotificationMapper {


    /**
     * 新增
     * @param notification
     */
    @Insert("insert into notification (notifier,receiver,outer_id,type,gmt_create,status,question_id) values (#{notifier},#{receiver},#{outerId},#{type},#{gmt_create},#{status},#{questionId})")
    int insert(Notification notification);

    /**
     * 删
     * @param notification
     * @return
     */
    @Delete("delete from notification where id = #{id}")
    int delete(Notification notification);

    /**
     * 更新消息,将未读变成已读
     */
    @Update("update notification set status = 1 where id = #{id}")
    void update(Long id);

    /**
     * 查
     * @param id
     * @return
     */
    @Select("select * from notification where  id = #{id}")
    Notification select(Long id);

    /**
     * 查询我的关于我的通知
     * @param userId
     * @return
     */
    @Select("select * from notification where receiver = #{receiver} and status = 0 order by gmt_create desc")
    List<Notification> findNotificationList(@Param("receiver") Long userId);

    /**
     * 查询关于我的未读通知，包括问题和用户
     * @param userId
     * @return
     */
    @Select("SELECT n.*,q.title ,c.content,u.`name` FROM notification n LEFT JOIN user u on n.notifier = u.id LEFT JOIN question q ON (n.outer_id = q.id AND n.type = 1) LEFT JOIN `comment` c on (n.outer_id = c.id and n.type = 2) where receiver = #{receiver}  order by gmt_create desc")
    List<Map<String, Object>> findNotificationListMap(@Param("receiver")Long userId);

    /**
     * 关于我的未读通知
     * @param userId
     * @return
     */
    @Select("select count(1) from notification where receiver = #{receiver} and status = #{status}")
    int count(@Param("receiver")Long userId,@Param("status")Integer status);
}
