package com.scitc.blog.service;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.mapper.UserInfoMapper;
import com.scitc.blog.model.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(UserInfoServiceTest.class);

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testRegisterUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("小咸鱼2333");
        userInfo.setUserEmail("136190@qq.com");
        userInfo.setPassword("123456");
        OperationExecution oe = userInfoService.registerUserInfo(userInfo);
        Assert.assertEquals(oe.getState(), OperationEnums.SUCCESS.getState());
    }

    @Test
    public void tesUpdateUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(9);
        userInfo.setUsername("111");
        userInfo.setUserEmail("1111");
//        userInfo.setPassword("123456");
        userInfo.setUserExplain("哈哈哈哈");
        ImageHolder imageHolder = new ImageHolder();
        imageHolder.setImage(null);
        imageHolder.setImageName("123456789");
        OperationExecution oe = userInfoService.updateUserInfo(userInfo, imageHolder);
        Assert.assertEquals(oe.getState(), OperationEnums.SUCCESS.getState());
    }

    @Test
    public void tesUpdatePsw() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(9);
        userInfo.setPassword("626532");
        String newPassword = "147852";
        OperationExecution op = userInfoService.updateUserPsw(userInfo, newPassword);

        Assert.assertEquals(OperationEnums.SUCCESS.getState(), op.getState());
    }

    @Test
    public void  tesLogin(){
        String name = "62653295590@qq.com";
       String password = "admin";
        OperationExecution op =  userInfoService.login(name,password);

        Assert.assertEquals(OperationEnums.SUCCESS.getState(), op.getState());
    }

    @Test
    public void fun1() {
//        String str = "        ";
//        String strTrim = str.trim();
//        Boolean isStr = str.trim()==null;
//        int length = strTrim.length();
//        Boolean last = str.trim().isEmpty();
//        logger.info("str:" +str + ":str");
////        logger.info("strTrim:" +strTrim + ":strTrim");
//        logger.info("isStr:" + isStr + ":isStr");
//        logger.info("length:" + length + ":length");
//        logger.info("last:" + last + ":last");

        String str1 = "小咸鱼2333444";
        String str2 = "小咸鱼2333444";
        boolean flog = str1.equals(str2);
        logger.info("last:" + flog + ":last");
    }
}
