package life.zwp.community.controller;

import life.zwp.community.dto.AccessTokenDTO;
import life.zwp.community.dto.GithubUser;
import life.zwp.community.mapper.UserMapper;
import life.zwp.community.model.User;
import life.zwp.community.provider.GithubProvider;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser userInfo = githubProvider.getUserInfo(accessToken);
     //   System.out.println(userInfo.getName()+userInfo.getId()+userInfo.getBio());
        if(userInfo !=null){
            //登录成功，写cookie和session.写入数据库
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(userInfo.getId()));
            user.setName(userInfo.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.insert(user);
          //  request.getSession().setAttribute("user",userInfo);
            //写入cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            // 登录失败，
            return "redirect:/";
        }
    }
}
