package life.zwp.community.service.serviceimpl;

import life.zwp.community.service.EmailService;
import life.zwp.community.utils.RandCodeEnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service("mailServiceImpl")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender jms;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;
    @Override
    public Boolean sentCode(String email) {
        //生成随机的4位数字
        String code = RandCodeEnumUtils.NUMBER_CHAR.generateStr(4);
        String emailContent = "验证码："+code;
        Boolean flag = sendSimpleEmail(emailContent, email);
        return flag;
    }


    /**
     *
     * @param data
     * @param email
     * @return
     */
    public Boolean sendSimpleEmail(String data,String email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 谁发起
            message.setFrom(from);
            //发送给谁，可以为多个，接受地址
            message.setTo("422514334@qq.com");
            //标题
            message.setSubject("一封简单的邮件");
            //内容
            message.setText(data);
            jms.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
