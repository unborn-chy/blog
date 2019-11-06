package com.scitc.blog.model;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String commentContent;
    private UserInfo userInfoFrom;
    private Article article;
    private Date createTime;
    private Integer toId;
    private Integer parentId;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public UserInfo getUserInfoFrom() {
        return userInfoFrom;
    }

    public void setUserInfoFrom(UserInfo userInfoFrom) {
        this.userInfoFrom = userInfoFrom;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", userInfoFrom=" + userInfoFrom +
                ", article=" + article +
                ", createTime=" + createTime +
                ", toId=" + toId +
                ", parentId=" + parentId +
                '}';
    }
}
