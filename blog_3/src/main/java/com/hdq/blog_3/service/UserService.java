package com.hdq.blog_3.service;

import com.hdq.blog_3.po.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User checkUser(String username,String password);
}
