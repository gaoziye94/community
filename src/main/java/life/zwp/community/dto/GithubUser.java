package life.zwp.community.dto;

import lombok.Data;

/**
 * 从github上获取用户信息，封装有用的字段
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
