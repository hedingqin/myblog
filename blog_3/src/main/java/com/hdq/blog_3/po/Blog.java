package com.hdq.blog_3.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//博客类
//@Table(name="t_blog")可以自动生成数据库表，表名叫t_blog
@Entity
@Table(name="t_blog")
public class Blog {
//    @Id
//    @GeneratedValue
//    以上两个注解表示标识id，并且自动生成id值
    @Id
    @GeneratedValue
    private  Long id;
    private String title; //标题

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content; //内容
    private String firstPicture; //首图
    private String flag; //标记：原创或转载
    private Integer views; //浏览次数
    private boolean appreciation; //赞赏开启
    private boolean shareStatement; //版权开启
    private boolean commentabled; //评论开启
    private boolean published; //发布
    private boolean recommend; //是否推荐
    @Temporal(TemporalType.TIMESTAMP)//指定生成的对应数据库表的时间格式
    private Date createTime; //创建时间
    @Temporal(TemporalType.TIMESTAMP)//指定生成的对应数据库表的时间格式
    private Date updateTime; //更新时间
    //一个博客只能有一个类型
    @ManyToOne
    private Type type;
    //级联关系可以进行级联新增，也就是在Blog页面新增一个标签时，也可以将此标签添加到数据库
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
//    @Transient   使成员变量不会与数据库映射
    @Transient
    private String tagIds;//前端传过来的标签是多个id值组成的字符串
    private String description; //博客描述
    //创建Blog对象时，也实例化Type对象
    public Blog(){
        this.type = new Type();
    }
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag : tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
               ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
