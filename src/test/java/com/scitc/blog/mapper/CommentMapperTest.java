package com.scitc.blog.mapper;

import com.scitc.blog.model.Article;
import com.scitc.blog.model.Comment;
import com.scitc.blog.model.UserInfo;
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

public class CommentMapperTest {
    private final static Logger logger = LoggerFactory.getLogger(CommentMapperTest.class);
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void testInsertComment() {
        Comment comment = new Comment();
        comment.setCommentContent("我是评论的内容4");

        Article article = new Article();
        article.setArticleId(63);
        comment.setArticle(article);

        UserInfo userInfoFrom = new UserInfo();
        userInfoFrom.setUserId(1);
        UserInfo userInfoTo = new UserInfo();
        userInfoTo.setUserId(2);
        comment.setUserInfoFrom(userInfoFrom);
        comment.setToId(2);
        comment.setCreateTime(new Date());
        int effectedNum  =commentMapper.insertComment(comment);
        Assert.assertEquals(1,effectedNum);

        logger.info("comment{}:",comment);

    }
    @Test
    public void testDeleteComment(){
        Integer commentId = 1;
        int effectedNum = commentMapper.deleteComment(commentId);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryCommentById(){

        Comment current = commentMapper.queryCommentById(8);
        logger.info("current:{}",current);
    }

    @Test
    public void testQueryComment(){

        Comment comment = new Comment();

        Article article = new Article();
        article.setArticleId(63);
        comment.setArticle(article);
        List<Comment> commentList = commentMapper.queryComment(comment,0,1000);
        logger.info("current:{}",commentList);
    }
}