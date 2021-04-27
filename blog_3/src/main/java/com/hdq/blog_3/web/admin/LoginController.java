package com.hdq.blog_3.web.admin;

import com.hdq.blog_3.po.User;
import com.hdq.blog_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
//Get请求是在地址栏输入一个地址或者点击某个连接
//Post请求是发送一个表单请求，参数都在实体内容当中
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";

        }else{
            attributes.addFlashAttribute("message","用户名和密码错误！");
            //此处错误信息不能使用model.addAttribute()，因为错误信息在重定向到登录页面时，拿不到
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";

    }
}
