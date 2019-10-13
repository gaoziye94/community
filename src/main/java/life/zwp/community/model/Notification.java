package life.zwp.community.model;
import lombok.Data;

/**
 * 提醒
 */
@Data
public class Notification {
    private Long id;
//    通知发起者
    private Long notifier;
//    通知接受者
    private Long receiver;
//    问题或者回复id
    private Long outerId;
//    评论1或者回复2
    private Integer type;
//    创建时间
    private Long gmt_create;
//    消息状态，未读0，已读1
    private Integer status;
//    所属问题
    private Long questionId;
}
