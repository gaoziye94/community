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
    public String publish(HttpServletRequest request, HttpServletResponse response,
                        Model model,  @RequestParam(value = "id",defaultValue = "0") Integer id){
        getUserFormSession(request);
        //根据id 回显问题
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question",question);
        return "publish";
    }

    /**
     * 发布问题，并返回当前页面
     * @return
     */
    @PostMapping("")
    public String create(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tags") String tags,
            HttpServletRequest request,
            Model model
    ){
        User user = getUser(request);
        if(user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //当前用户id
        int creator = user.getId();
        Question question = new Question();
        if(id ==null){
            question.setTitle(title);
            question.setDescription(description);
            question.setTags(tags);
            //新增
        } else {
            //修改
            //不为空，为编辑,先查询出这个问题
            question = questionService.findQuestionById(id);
        }
        model.addAttribute("question",question);
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
        //判断是新增还是编辑。有无id
        if(id ==null){
            //新增
            question.setCreator(creator);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionService.create(question);
        } else {
            //不为空，为编辑,先查询出这个问题
            question = questionService.findQuestionById(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTags(tags);
            question.setGmtModified(System.currentTimeMillis());
            questionService.update(question);
        }

        return "redirect:/";
    }

    private User getUser(HttpServletRequest request) {
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
        return user;
    }
}
