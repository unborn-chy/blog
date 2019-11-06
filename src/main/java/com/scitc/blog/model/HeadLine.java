package com.scitc.blog.model;

import java.util.Date;

public class HeadLine {
    private Integer lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Date createTime;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "HeadLine{" +
                "lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", lineLink='" + lineLink + '\'' +
                ", lineImg='" + lineImg + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
