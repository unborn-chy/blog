package com.scitc.blog.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserInfo{
    private Integer userId;
    private String username;
    private String password;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String userEmail;
    private String userImg;
    private Integer userType;
    private String userExplain;
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserExplain() {
        return userExplain;
    }

    public void setUserExplain(String userExplain) {
        this.userExplain = userExplain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userType=" + userType +
                ", userExplain='" + userExplain + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
