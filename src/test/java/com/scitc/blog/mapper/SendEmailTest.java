package com.scitc.blog.mapper;

import com.scitc.blog.model.UserInfo;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.EmailUtils;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTest {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EmailUtils emailUtils;

    @Test
    public void sendMail() {
        String toUser = "13619077004@163.com";
        String newPassword = CommonUtil.getStringRandom(12);
        logger.info("newPassword:{}", newPassword);
//        emailUtils.sendEmail(toUser,newPassword);
        boolean sendFlag = emailUtils.sendEmail(toUser,newPassword);
        if(sendFlag){
            logger.info("成功");
        }else {
            logger.info("失败");
        }

    }

    @Test
    public void getUuid() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUsername("admin");
        userInfo.setPassword("123456789");
        userInfo.setUserImg("localhost:8080/123.jpg");

        String userStr = JSONObject.fromObject(userInfo).toString();
        logger.info("userStr:{}",userStr);

        JSONObject jsonObject=JSONObject.fromObject(userStr);
        UserInfo getUser=(UserInfo)JSONObject.toBean(jsonObject, UserInfo.class);

        logger.info("getUser:{}",getUser.getUserImg());

    }
}
