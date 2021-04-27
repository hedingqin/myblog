package com.hdq.blog_3.service;

import com.hdq.blog_3.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
