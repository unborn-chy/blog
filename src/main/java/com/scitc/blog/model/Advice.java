package com.scitc.blog.model;

import java.util.Date;

public class Advice {
    private Integer adviceId;
    private String adviceContent;
    private Date createTime;

    public Integer getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Integer adviceId) {
        this.adviceId = adviceId;
    }

    public String getAdviceContent() {
        return adviceContent;
    }

    public void setAdviceContent(String adviceContent) {
        this.adviceContent = adviceContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Advice{" +
                "adviceId=" + adviceId +
                ", adviceContent='" + adviceContent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
