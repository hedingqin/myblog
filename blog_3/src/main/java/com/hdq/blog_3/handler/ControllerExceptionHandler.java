package com.hdq.blog_3.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


//异常处理类：拦截有Controller注解的控制器的响应请求
@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger=  LoggerFactory.getLogger(this.getClass());

//    @ExceptionHandler---可以拦截Exception级别的异常，比如runtimeException
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);

//        利用注解工具去识别一些异常抛出，如果这些异常抛出有状态码，说明是我们自己创捷的异常类，因此可以单独处理
         if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
             throw e;
         }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");//返回error页面
        return mv;
    }
}
