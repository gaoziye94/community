package life.zwp.community.controller;

import life.zwp.community.model.User;
import life.zwp.community.service.NotificationService;
import life.zwp.community.service.QuestionService;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    private NotificationService notificationService;
    protected void unreadCount(HttpServletRequest request, Model model) {
        //关于我的通知未读条数
        User userInfo = (User)request.getSession().getAttribute("user");
        Integer unreadCount = 0;
        if(userInfo != null){
            unreadCount =  notificationService.myCount(userInfo.getId());
        }
        model.addAttribute("unreadCount",unreadCount);
    }
}
