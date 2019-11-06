package com.scitc.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender mailSender; //框架自带的

    @Value("${spring.mail.username}")  //发送人的邮箱  比如155156641XX@163.com
    private String from;

    public boolean sendEmail(String toUser, String newPassword) {
        String html = "<!DOCTYPE html>\r\n" +
                "<html>\r\n" +
                "<head>\r\n" +
                "<meta charset=\"UTF-8\">\r\n" +
                "<title>Insert title here</title>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "你的新密码是: <font color=\"red\">" + newPassword + "</font>\r\n" +
                "</body>\r\n" +
                "</html>";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("小咸鱼博客：你的新密码");
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
