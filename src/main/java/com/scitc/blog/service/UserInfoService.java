package com.scitc.blog.service;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.UserInfo;

public interface UserInfoService {
    //注册用户
    OperationExecution registerUserInfo(UserInfo userInfo) throws BlogException;

    //跟新用户 以及密码
    OperationExecution updateUserInfo(UserInfo userInfo, ImageHolder imageHolder) throws BlogException;

    OperationExecution updateUserPsw(UserInfo userInfo, String newPassword) throws BlogException;

    //删除用户
    OperationExecution deleteUserInfo(Integer userId) throws BlogException;

    //login
    OperationExecution login(String nameOrEmail, String password) throws BlogException;

    //后台用户列表
    OperationExecution getUserInfoList(int pageIndex, int pageSize) throws BlogException;

    //找回密码
    OperationExecution findByPassword(String username, String userEmail) throws BlogException;

    //查找用户
    UserInfo findByUserInfo(Integer userId, String username, String userEmail) throws BlogException;

    int updateUserType(Integer userId) throws BlogException;

}
