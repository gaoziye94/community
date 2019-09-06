package life.zwp.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private String headUrl;
    private String bio;
    private Long gmtCreate;
    private Long gmtModified;

}
