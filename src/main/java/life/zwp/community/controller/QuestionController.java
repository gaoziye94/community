package life.zwp.community.controller;

import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我的问题 -Controller
 */
@Controller("questionController")
@RequestMapping("/profile")
public class QuestionController extends BaseController{

    @Autowired
    private UserService userService;
    @GetMapping("/questions")
    public String myQuestion(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        getUserFormSession(request);
        return "questions";
    }
}
