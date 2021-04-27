package com.hdq.blog_3.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//评论类
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//内容
    private String avatar;//头像
    @Temporal(TemporalType.TIMESTAMP)//指定生成的对应数据库表的时间格式
    private Date createTime; //创建时间

    @ManyToOne
    private Blog blog;
    //以下是自关联关系
    //一个评论有多个子评论，而一个子评论只有一个父评论
    //只有通过这样的关系，才能从一个评论找到其所有子评论，从子评论能够找到其父评论，可以完美
    //形成评论的层次结构
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();//子回复列表

    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;//用于判断是否为博主评论

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
