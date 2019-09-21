package life.zwp.community.controller;

import life.zwp.community.dto.QuestionDTO;
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
    public String detail( Model model,@RequestParam(value = "id") Integer id) {

        QuestionDTO questionDTO = questionService.findQuestionDTOById(id);
//        model.addAttribute("name",id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }

}
