package life.zwp.community.dto;

import life.zwp.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmt_create;
//    消息状态，已读未读
    private Integer status;
//    消息发起者
    private User notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
    private String type;
}
