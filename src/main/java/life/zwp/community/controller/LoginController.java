package life.zwp.community.controller;

import life.zwp.community.dto.Message;
import life.zwp.community.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录 controller
 */
@Controller("loginController")
public class LoginController {

    @Autowired
    private EmailService emailService;
    //登录页面
    @GetMapping("/account/login")
    public String login(){
        return "login";
    }

    //注册页面
    @GetMapping("/account/register")
    public String register(){
        return "register";
    }

    /**
     * 点击发送验证码到邮箱
     * @return
     */
    @GetMapping("/account/register/sentcode")
    @ResponseBody
    public Message sentCode(String email){
       Boolean flag =  emailService.sentCode(email);
       if(flag){
           return Message.commonSuccess();
       }
       return Message.commonError("10000","失败");
    }
}
