package com.hdq.blog_3.service;

import com.hdq.blog_3.dao.CommentRepository;
import com.hdq.blog_3.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
//        默认升序排序
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }


    private List<Comment> tempReply = new ArrayList<>();//存储第一级评论下的所有评论的列表

    //取出第一级评论，存储到另一个list中，以免更改数据库
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentList = new ArrayList<>();
        for(Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentList.add(c);
        }
        for(Comment comment : commentList){
            if(comment.getReplyComments().size()>0){
                iterSubComments(comment.getReplyComments());//取出第二级评论
                List<Comment> comments1 = new ArrayList<>();
                for(Comment c : tempReply){
                    Comment comment1 = new Comment();
                    BeanUtils.copyProperties(c,comment1);
                    comments1.add(comment1);
                }
                    comment.setReplyComments(comments1);
                    tempReply.clear();
            }
        }
        return commentList;
    }

    //遍历第二级评论
    private void iterSubComments(List<Comment> comments){
        for(Comment comment : comments){
            getAllSubComments(comment);
        }
    }

    //将所有第一级评论下的所有评论都递归遍历出来
    private void getAllSubComments(Comment comment){
        tempReply.add(comment);
        if(comment.getReplyComments().size()>0){
            for(Comment c : comment.getReplyComments()){
                getAllSubComments(c);
            }

        }
    }
}
