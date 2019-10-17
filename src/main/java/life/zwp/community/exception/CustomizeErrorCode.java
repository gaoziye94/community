package life.zwp.community.exception;

public enum CustomizeErrorCode implements CustomizeErrorCodeInterface{
    QUESTION_NOT_FOUND("2001","你找的问题不存在，请换个试试"),
    COMMENT_QUESTION_NOT_FOUND("2002","你评论或者回复的问题不存在"),
    COMMENT_TYPE_WRONG("2002","评论类型错误或者不存在"),
    COMMENT_NOT_FOUND("2003","回复的评论不存在"),
    COMMENT_CONTENT_IS_BLANK("2004","评论内容不能为空"),
    NOTICE_IS_BLANK("2005","通知不存在"),
    READ_NOTICE_FAIL("2006","你这是在读别人的信息？"),
    File_UPLOAD_FAIL("2007","文件上传失败");
    private String message;
    private String code;
    CustomizeErrorCode(String code,String message) {
        this.message = message;
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return null;
    }
}
