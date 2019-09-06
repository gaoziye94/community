package life.zwp.community.controller;

import life.zwp.community.model.Question;
import life.zwp.community.model.User;
import life.zwp.community.service.QuestionService;
import life.zwp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 发起问题controller
 */
@Controller("publishController")
@RequestMapping("/publish")
public class PublishController extends BaseController{

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String publish(HttpServletRequest request, HttpServletResponse response, Model model){
        getUserFormSession(request);
        return "publish";
    }

    /**
     * 发布问题，并返回当前页面
     * @return
     */
    @PostMapping("")
    public String create(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tags") String tags,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tags",tags);
        if(title ==null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description ==null || description == ""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tags ==null || tags == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        int creator = 0;
        // 获取登录人
        User user = new User();
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    user = userService.findByToken(token);
                    if(user !=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        creator = user.getId();
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(creator);
        questionService.create(question);
        return "redirect:/";
    }
}
