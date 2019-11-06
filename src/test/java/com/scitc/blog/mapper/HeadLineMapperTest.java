package com.scitc.blog.mapper;

import com.scitc.blog.model.HeadLine;
import com.scitc.blog.model.Tags;
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
public class HeadLineMapperTest {

    @Autowired
    private HeadLineMapper headLineMapper;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void testInsertHeadLine(){
        HeadLine headLine = new HeadLine();
        headLine.setLineName("我是一个头条");
        headLine.setLineLink("https://www.bilibili.com/");
        headLine.setLineImg("https://i0.hdslb.com/bfs/album/9dbd56966a7fa7bf8fdca1dd4f800c52001db2a5.png");
        headLine.setCreateTime(new Date());
        int effectedNum = headLineMapper.insertHeadLine(headLine);
        Assert.assertEquals(1,effectedNum);
        logger.info("headLine:{}",headLine);
    }
    @Test
    public void testUpdateHeadLine(){
        HeadLine headLine = new HeadLine();
        headLine.setLineId(3);
        headLine.setLineName("我是一个头条修改");
        headLine.setLineLink("https://www.bilibili.com/");
        headLine.setLineImg("https://i0.hdslb.com/bfs/album/9dbd56966a7fa7bf8fdca1dd4f800c52001db2a5.png");
        headLine.setCreateTime(new Date());
        int effectedNum = headLineMapper.updateHeadLine(headLine);
        Assert.assertEquals(1,effectedNum);
        logger.info("headLine:{}",headLine);
    }

    @Test
    public void testQueryHeadLineById(){
        HeadLine headLine = headLineMapper.queryHeadLineById(1);
        logger.info("headLine：{}",headLine);
    }
//    @Test
//    public void testDeleteHeadLine(){
//        int effectedNum = headLineMapper.deleteHeadLine(2);
//        Assert.assertEquals(effectedNum,1);
//    }
    @Test
    public void testQueryHeadLineList(){

        List<HeadLine> headLineList = headLineMapper.queryHeadLineList();
        logger.info("headLineList:{}",headLineList);
    }



}