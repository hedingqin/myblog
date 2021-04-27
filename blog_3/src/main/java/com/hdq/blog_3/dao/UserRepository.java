package com.hdq.blog_3.dao;

import com.hdq.blog_3.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository<User,Long>中Long表示的是User的主键是Long类型的
public interface UserRepository extends JpaRepository<User,Long> {
//    本方法是自定义的，没有写访问数据库的代码也能够查询到User对象，很奇怪
    User findByUsernameAndPassword(String username,String password);
}
