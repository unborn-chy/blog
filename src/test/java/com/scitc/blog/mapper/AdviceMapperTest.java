package com.scitc.blog.mapper;

import com.scitc.blog.model.Advice;
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
public class AdviceMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(AdviceMapperTest.class);
    @Autowired
    private AdviceMapper adviceMapper;
    @Test
    public void testInsertAdvice(){
        Advice advice = new Advice();
        advice.setAdviceContent("哈哈哈哈");
        advice.setCreateTime(new Date());
        int effectedNum = adviceMapper.insertAdvice(advice);
        Assert.assertEquals(1,effectedNum);
        logger.info("advice:{}",advice);
    }

    @Test
    public void testUpdateAdvice(){
        Advice advice = new Advice();
        advice.setAdviceId(3);
        advice.setAdviceContent("嘿嘿");

        int effectedNum = adviceMapper.updateAdvice(advice);
        Assert.assertEquals(1,effectedNum);
        logger.info("advice:{}",advice);

    }

    @Test
    public void testQueryAdviceById(){
        Integer adviceId = 2;
        Advice advice= adviceMapper.queryAdviceById(adviceId);
        logger.info("advice:{}",advice);
    }

    @Test
    public void testQueryAdvice(){

        List<Advice> adviceList= adviceMapper.queryAdvice();
        logger.info("adviceList:{}",adviceList);
    }

    @Test
    public void testDeleteAdvice(){

        int effectedNum = adviceMapper.deleteAdvice(2);
        Assert.assertEquals(1,effectedNum);
    }

}
