package life.zwp.community.controller;

import life.zwp.community.dto.PaginationDTO;
import life.zwp.community.model.User;
import life.zwp.community.service.NotificationService;
import life.zwp.community.service.QuestionService;
import life.zwp.community.service.UserService;
import life.zwp.community.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "10") Integer size,
                        @RequestParam(value = "search",required = false) String search){

        //获取首页问题列表,和发布用户头像
        //userId =0 ,查询所有人的问题
        PaginationDTO paginationDTO =  questionService.findQuestion(page,size,search);
        LogUtils.logInfo("=================="+paginationDTO.getTotalCount());
        model.addAttribute("paginationDTO",paginationDTO);
        model.addAttribute("search",search);
        //我的未读消息
        unreadCount(request,model);
        //热门话题
        return "index";
    }


}
