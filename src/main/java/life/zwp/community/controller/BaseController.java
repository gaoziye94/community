package life.zwp.community.controller;

import life.zwp.community.model.User;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Autowired
    private  UserService userService;
    public  void getUserFormSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user1 = userService.findByToken(token);
                    request.getSession().removeAttribute("user");
                    if (user1 != null) {
                        request.getSession().setAttribute("user", user1);
                    }
                    break;
                }
            }
        }
    }
}
