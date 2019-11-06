package com.scitc.blog.mapper;

import com.scitc.blog.model.UserInfo;
import org.apache.catalina.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {
   private static final Logger logger = LoggerFactory.getLogger(UserInfoMapperTest.class);
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Test
    public void testInsertUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("测试用户1");
        userInfo.setPassword("123456");
        userInfo.setUserEmail("123@qq.com");
        userInfo.setUserType(1);
        userInfo.setUserExplain("无");
        userInfo.setUserImg("http:8080/123");
        userInfo.setCreateTime(new Date());
        int effectedNum = userInfoMapper.insertUserInfo(userInfo);

        logger.info("userInfo:::{}",userInfo);
        Assert.assertEquals(1,effectedNum);

    }

    @Test
    public void testQueryUserInfo(){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(2);
//        userInfo.setUsername("12312312121");
//        userInfo.setUserEmail("626532990@qq.com");
//        userInfo.setPassword("123456");
        UserInfo getUserInfo = userInfoMapper.queryUserInfo(null,"test","626532990@qq.com");
        logger.info("getUserInfo:{}",getUserInfo);
    }
    @Test
    public void testUpdateUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(4);
        userInfo.setUsername("测试用户3");
        userInfo.setPassword("1234567");
        userInfo.setUserEmail("1233@qq.com");
        userInfo.setUserType(1);
        userInfo.setUserExplain("无话可说");
        userInfo.setUserImg("http:8080/123");
        userInfo.setCreateTime(new Date());

        int effectedNum = userInfoMapper.updateUserInfo(userInfo);
        logger.info("userInfo:::{}",userInfo);
        Assert.assertEquals(1,effectedNum);
    }
    @Test
    public void testQueryUserInfoList(){
       List<UserInfo> userInfoList =  userInfoMapper.queryUserInfoList(1,2);
       logger.info("userInfoList{}",userInfoList);
    }

    @Test
    public void testDeleteTags(){

        int effectedNum = userInfoMapper.deleteUserInfo(4);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryUserCount(){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername("test");
//        userInfo.setUserEmail("123@qq.com");
//        int count = userInfoMapper.queryUserInfoCount("test",null);
//        logger.info("count: {}",count);
    }

}