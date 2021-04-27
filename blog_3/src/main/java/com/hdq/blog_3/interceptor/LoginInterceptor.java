package com.hdq.blog_3.interceptor;
//非法访问博客管理页面拦截器
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//防止其它访客直接登录博客管理页面对我们的博客进行危害
//因此在访问这些页面时，要做一个页面拦截器，就是在访问这些页面的时候要首先判断管理员是否登录
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //若未登录，则返回登录页面
        if(request.getSession().getAttribute("user") == null)
        {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
