package life.zwp.community.advice;

import life.zwp.community.dto.Message;
import life.zwp.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义处理异常，跳转到错误页面,获取信息
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ModelAndView handle(HttpServletRequest request, Model model, Throwable ex){
        String message = ex.getMessage();
        //返回json
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        //返回页面
        ModelAndView mv2 = new ModelAndView("error");
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回json
            mv.addObject("message", message);
            return mv;
        } else {
            //返回视图
            if(ex instanceof CustomizeException){
                mv2.addObject("message",message);
            }else {
                mv2.addObject("message","操作错误");
            }
            return mv2;
        }
    }
}
