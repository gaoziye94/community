package life.zwp.community.dto;

/**
 * 返回信息
 */
public class Message {
    /**
     * 类型
     */
    public enum Type {

        /** 成功 */
        success,

        /** 警告 */
        warn,

        /** 错误 */
        error
    }

    /** 类型 */
    private Type type;

    /**
     * 编码
     */
    private String code;
    /**
     * 信息
     */
    private String msg;

    /** 内容 */
    private Object content;


    public static Message MESSAGE_SAVE_SUCCESS = new Message(Type.success, "10000", "保存成功");
    public static Message MESSAGE_DEL_SUCCESS = new Message(Type.success, "10000", "删除成功");
    public static Message MESSAGE_DEL_ERROR = new Message(Type.error, "10000", "删除失败");

    public static Message LOGIN_SUCCESS = new Message(Type.success, "10000", "登录成功");
    public static Message NO_LOGIN = new Message(Type.error, "20000", "当前操作需要登录");

    public Message() {
    }
    public Message(Type type, String code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }
    public Message(Type type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    private static Message error(String code, String msg) {
        return new Message(Type.error, code, msg);
    }
    private static Message success(String code, String msg) {
        return new Message(Type.success, code, msg);
    }

    //返回成功
    public static Message commonSuccess(){
        Message msg =  Message.success("10000", "成功");
        return msg;
    }
    //返回失败
    public static Message commonError(){
        Message msg =  Message.error("10000", "失败");
        return msg;
    }
    public static Message commonSuccess(Object data){
        Message msg =  Message.success("10000", "成功");
        msg.setContent(data);
        return msg;
    }

    //返回失败，携带原因
    public static Message commonError(String code,String msg){
        Message message = Message.error(code, msg);
        return message;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
