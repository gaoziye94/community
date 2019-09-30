package life.zwp.community.service;

/**
 * 邮箱相关接口
 */
public interface EmailService {
    /**
     * 发送验证码接口
     * @return
     * @param email
     */
    Boolean sentCode(String email);
}
