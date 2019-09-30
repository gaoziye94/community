package life.zwp.community.controller;

import life.zwp.community.dto.QuestionDTO;
import life.zwp.community.model.Comment;
import life.zwp.community.model.Question;
import life.zwp.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller("questionController")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    /**
     * 问题详情页
     * @param model
     * @return
     */
    @GetMapping("")
    public String detail( Model model,@RequestParam(value = "id") Long id) {

        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
//        model.addAttribute("name",id);
//        浏览数增加1
        Question question = new Question();
        question.setId(questionDTO.getQuestion().getId());
//        多个人同事访问会产生并发
        question.setViewCount(1);
        questionService.updateViewCount(question);
        model.addAttribute("questionDTO",questionDTO);
//        加载评论列表,1级评论
        //1级评论
        Integer type = 1;
        List<Map<String,Object>> commentList =  questionService.commentList(id,type);
        model.addAttribute("commentList",commentList);
        return "question";
    }

}
