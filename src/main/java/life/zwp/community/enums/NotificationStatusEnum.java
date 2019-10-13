package life.zwp.community.enums;

/**
 * 消息已读或者未读
 */
public enum NotificationStatusEnum {
    UNREAD(0,"未读"),
    READ(1,"已读");

    private Integer type;
    private String typeName;

    NotificationStatusEnum(){};

    NotificationStatusEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }}
