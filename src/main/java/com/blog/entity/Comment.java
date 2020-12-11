package com.blog.entity;

import java.util.Date;

//评论模块
public class Comment {
    private Long commentId;

    private Long blogId; //关联的博客ID

    private String commentator; //评论者名称

    private String email;  //评论者邮箱

    private String commentBody; //评论内容

    private Date commentCreateTime;  //评论提交时间

    private String blogTitle;  //评论时的IP地址

    private String replyBody;  //回复内容

    private Date replyCreateTime;  //回复时间

    private Byte commentStatus;  //评论审核状态  0-未审核，1-审核通过

    private Byte isDeleted;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator == null ? null : commentator.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody == null ? null : commentBody.trim();
    }

    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle == null ? null : blogTitle.trim();
    }

    public String getReplyBody() {
        return replyBody;
    }

    public void setReplyBody(String replyBody) {
        this.replyBody = replyBody == null ? null : replyBody.trim();
    }

    public Date getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(Date replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }

    public Byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", blogId=" + blogId +
                ", commentator='" + commentator + '\'' +
                ", email='" + email + '\'' +
                ", commentBody='" + commentBody + '\'' +
                ", commentCreateTime=" + commentCreateTime +
                ", blogTitle='" + blogTitle + '\'' +
                ", replyBody='" + replyBody + '\'' +
                ", replyCreateTime=" + replyCreateTime +
                ", commentStatus=" + commentStatus +
                ", isDeleted=" + isDeleted +
                '}';
    }
}