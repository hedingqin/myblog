package com.hdq.blog_3.dao;

import com.hdq.blog_3.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    //按分页的方式去查询，因为分页数据可以指定size大小
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
