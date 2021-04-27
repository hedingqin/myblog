package com.hdq.blog_3.service;

import com.hdq.blog_3.po.Blog;
import com.hdq.blog_3.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    //条件查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    //根据标签查询
    Page<Blog> listBlog(Long tagId,Pageable pageable);

//    搜索框搜索查询
    Page<Blog> listBlog(String query,Pageable pageable);

    //查询最新发布的博客列表
    List<Blog> listRecommendBlogTop(Integer size);

//将markdown文本转化成HTML文本
    Blog getAndConvert(Long id);

    //根据更新日期的年份查询blog，每个年份对应多个blog
    Map<String,List<Blog>> archiveBlog();

    //总博客数
    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
