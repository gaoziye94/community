package life.zwp.community.dto;

import lombok.Data;

/**
 *  评论dto
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
