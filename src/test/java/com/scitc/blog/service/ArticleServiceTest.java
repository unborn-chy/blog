package com.scitc.blog.service;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Article;
import com.scitc.blog.model.Category;
import com.scitc.blog.model.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Test
    public void testAddArticle() throws Exception {
        Article article = new Article();
        article.setArticleTitle("我是一个测试标题Service");
        article.setArticleContent("我是测试内容Service");
        article.setArticleTags("java,php,Service");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserType(0);

        Category category = new Category();
        category.setCategoryId(2);

        article.setUserInfo(userInfo);
        article.setCategory(category);

        //图片
        File file = new File("E:/photo/abc.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(), inputStream);

        //位置
        String contentPath = "/blog";
        OperationExecution ae = articleService.addArticle(article,imageHolder);

        Assert.assertEquals(OperationEnums.INNER_SUCCESS.getState(),ae.getState());
    }

    @Test
    public void testUpdateArticleService() throws Exception {
        Article article = new Article();
        article.setArticleId(5);
        article.setArticleTitle("我是一个测试标题修改Service444");
        article.setArticleContent("我是测试内容Service444");
        article.setArticleTags("java,php,Service");

        Category category = new Category();
        category.setCategoryId(2);

        article.setCategory(category);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserType(0);

        article.setUserInfo(userInfo);


        //图片
        File file = new File("E:/photo/abc.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(), inputStream);

        //位置
        OperationExecution ae = articleService.updateArticle(article,imageHolder);

        Assert.assertEquals(OperationEnums.UPDATE_SUCCESS.getState(),ae.getState());
    }
}
