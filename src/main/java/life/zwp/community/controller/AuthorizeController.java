package life.zwp.community.controller;

import life.zwp.community.dto.AccessTokenDTO;
import life.zwp.community.dto.GithubUser;
import life.zwp.community.mapper.UserMapper;
import life.zwp.community.model.User;
import life.zwp.community.provider.GithubProvider;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * 登录github授权
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;

    @GetMapping("/account/github/login")
    public String githubLogin() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=eaf760769d322e005e06&redirect_uri=http://localhost:9090/callback&scope=user&state=1";
    }


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response,
                           Model model){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser userInfo = githubProvider.getUserInfo(accessToken);
     //   System.out.println(userInfo.getName()+userInfo.getId()+userInfo.getBio());
        String token = UUID.randomUUID().toString();
        if(userInfo !=null){
            //登录成功，写cookie和session.写入数据库
            //查看数据库中有没有这个人 根据 AccountId，唯一的，如果有，就更新token时间，没有就新增
            User user = new User();
//            user.setAccountId(String.valueOf(userInfo.getId()));
            User user1 = userService.findByAccountId(String.valueOf(userInfo.getId()));
            if(user1 !=null){
                //更新这个用户信息，token
                user1.setToken(token);
                user1.setHeadUrl(userInfo.getAvatarUrl());
                user1.setName(userInfo.getName());
                user1.setBio(userInfo.getBio());
                user1.setGmtModified(System.currentTimeMillis());
                userService.update(user1);
            } else {
                user.setToken(token);
                user.setAccountId(String.valueOf(userInfo.getId()));
                user.setHeadUrl(userInfo.getAvatarUrl());
                user.setBio(userInfo.getBio());
                user.setName(userInfo.getName());
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userService.insert(user);
            }
          //  request.getSession().setAttribute("user",userInfo);
            //写入cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            // 登录失败，
            model.addAttribute("error","登录失败");
            return "redirect:/";
        }
    }
}
