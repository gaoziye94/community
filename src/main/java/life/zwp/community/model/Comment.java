package life.zwp.community.model;

import lombok.Data;

/**
 * 评论实体类
 */
@Data
public class Comment {
//    id
    private Long id;
//    问题id
    private Long parentId;
//    评论类型。0--》 1级评论；1--》2级评论
    private Integer type;
//    评论人
    private Long commentator;
//    评论内容
    private String content;
//    评论收藏数
    private Long likeCount;
//    回复数
    private Long replyCount;
    private Long gmtCreate;
    private Long gmtModified;

    public enum CommentType {
        COMMENT(1),
        REPLY(2);
        private Integer type;
        CommentType(Integer type) {
            this.type = type;
        }
        public Integer getType() {
            return type;
        }
        public void setType(Integer type) {
            this.type = type;
        }

        public static Boolean isExeist(Integer type){
            for (CommentType commentType : CommentType.values()) {
                if(type ==commentType.getType()){
                    return true;
                }
            }
            return false;
        }
    };
}
