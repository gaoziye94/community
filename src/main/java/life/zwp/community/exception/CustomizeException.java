package life.zwp.community.exception;

/**
 * 自定义异常
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private String code;


    public CustomizeException(CustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
