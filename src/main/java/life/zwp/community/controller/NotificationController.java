package life.zwp.community.controller;

import life.zwp.community.model.Notification;
import life.zwp.community.model.User;
import life.zwp.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消息controller
 */
@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    /**
     * 跳转到相应的问题
     * @return
     */
    @GetMapping("/{id}")
    private String question(HttpServletRequest request, HttpServletResponse response, Model model,
                            @PathVariable(name = "id") Long id){
        User user = (User) request.getSession().getAttribute("user");
        if(user ==null){
            return "redirect:/";
        }
        Notification notification = notificationService.select(id,user);
        Long questionId = notification.getQuestionId();
        //跳转到对应的问题
        //修改消息为已读
        notificationService.update(id);
        return  "redirect:/question/"+questionId;
    }
}
