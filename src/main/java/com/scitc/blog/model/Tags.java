package com.scitc.blog.model;

import java.util.Date;

public class Tags {
    private Integer tagId;
    private String tagName;
    private Date createTime;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
