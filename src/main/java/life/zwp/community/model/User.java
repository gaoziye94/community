package life.zwp.community.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private String headUrl;
    private String bio;
    /*账号类型 第三方登录==1，邮箱注册==2*/
    private Integer accountType;
    private Long gmtCreate;
    private Long gmtModified;

}
