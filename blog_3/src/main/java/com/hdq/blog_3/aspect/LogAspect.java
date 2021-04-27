package com.hdq.blog_3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//日志处理：定义该类之后，就会有日志文件输出了
//@Aspect注解可以进行一些切面的操作，@Component注解可以使得Springboot扫描这个类
@Aspect
@Component
public class LogAspect {
//    从前端请求到后端的某个类某个方法的响应的整个过程就是一条线，该在该线上的某一个点切一刀就形成了切面
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    //    execution(* com.hdq.blog_3.web.*.*(..))中第一个*号表示包括所有修饰符的类，如public，private等等
//    第二个*表示web下所有的类，第三个*表示所有的方法
    @Pointcut("execution(* com.hdq.blog_3.web.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url =request.getRequestURL().toString();//通过request得到URL
        String ip =request.getRemoteAddr();//通过request得到ip地址
        //通过joinPoint得到访问的类名以及参数名
        //getDeclaringTypeName得到类的绝对路径，getName()得到方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
//        logger.info("--------doAfter-------");
    }

//    在请求结束之后显示Controller中请求的方法的返回结果
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url; //请求的地址
            this.ip = ip;  //请求的ip
            this.classMethod = classMethod; //请求的方法名
            this.args = args; //请求的参数
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
