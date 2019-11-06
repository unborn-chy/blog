package com.scitc.blog.mapper;

import com.scitc.blog.model.Article;
import com.scitc.blog.model.Category;
import com.scitc.blog.model.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(ArticleMapperTest.class);
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testInsertArticle() {
        Article article = new Article();

        for (int i = 0; i < 20; i++) {
            article.setArticleTitle("我是一个测试标题");
            article.setArticleContent("我是测试内容");
            article.setArticleTags("java,php");
            article.setCreateTime(new Date());
            article.setLastEditTime(new Date());
            article.setArticleImg("http:8080");
            article.setArticleState(0);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(1);
            Category category = new Category();
            category.setCategoryId(2);
            article.setUserInfo(userInfo);
            article.setCategory(category);
            int effectNum = articleMapper.insertArticle(article);

            logger.info("effectNum:{}", effectNum);
            logger.info("articleId:{}", article.getArticleId());
            logger.info("articleI", article);
        }


    }

    @Test
    public void testQueryByArticleId() {
        Article article = articleMapper.queryByArticleId(1);
        logger.info("article:{}", article);
    }

    @Test
    public void testUpdateArticle() {
        Article article = new Article();
        article.setArticleId(5);

        article.setArticleTitle("3333333333333");
        article.setArticleContent("3333333333");
        article.setArticleTags("java,php,3333333333");
        article.setLastEditTime(new Date());
        article.setArticleImg("http:8080/3333333333333");
        article.setArticleState(0);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(2);
        Category category = new Category();
        category.setCategoryId(3);
        article.setUserInfo(userInfo);
        article.setCategory(category);
        int effectNum = articleMapper.updateArticle(article);

        logger.info("effectNum:{}", effectNum);
        logger.info("articleId:{}", article.getArticleId());
        logger.info("article---:{}", article);
    }

    @Test
    public void testQueryArticleList() {

        Article article = new Article();
//        article.setArticleTitle("修改");
//        article.setArticleState(0);
//        Category category = new Category();
//        category.setCategoryId(2);
//        article.setCategory(category);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1);
//        article.setUserInfo(userInfo);
        article.setArticleTags("javascript");
        List<Article> articleList = articleMapper.queryArticleList(article, 0, 10);
        logger.info("articleList：{}", articleList);
    }

    @Test
    public void testQueryArticleCount() {
        Article article = new Article();
        article.setArticleState(0);
        Category category = new Category();
        category.setCategoryId(2);
        article.setCategory(category);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        article.setUserInfo(userInfo);
        int effectedNum = articleMapper.queryArticleCount(article);
        logger.info("count：{}", effectedNum);
    }

    @Test
    public void deleteArticleById(){
        int effectedNum = articleMapper.deleteArticleById(54);
        logger.info("effectedNum{}",effectedNum);
        Assert.assertEquals(1,effectedNum);
    }

}

