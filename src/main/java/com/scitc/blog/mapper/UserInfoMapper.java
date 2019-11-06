package com.scitc.blog.mapper;

import com.scitc.blog.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    //添加
    int insertUserInfo(UserInfo userInfo);
    int updateUserInfo(UserInfo userInfo);
    int deleteUserInfo(Integer userId);

    UserInfo queryUserInfo(@Param("userId") Integer userId,@Param("username") String username,@Param("userEmail") String userEmail);
    List<UserInfo> queryUserInfoList(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    int queryUserInfoCount();

}
