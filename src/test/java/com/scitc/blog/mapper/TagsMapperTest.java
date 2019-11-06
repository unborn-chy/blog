package com.scitc.blog.mapper;

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
public class TagsMapperTest {

    @Autowired
    private TagsMapper tagsMapper;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void testInsertTags(){
        Tags tags = new Tags();
        String tagName = "java2";
        Date createTime = new Date();
        tags.setTagName(tagName);
        tags.setCreateTime(createTime);
        int i = tagsMapper.insertTags(tags);
        logger.info(i +"");
        System.out.println("i= " + i);
        logger.info(tags.getTagId() + "");
        System.out.println("tags.getTagId() = " +tags.getTagId());
    }

    @Test
    public void testQueryTagsById(){
        Tags tags = tagsMapper.queryTagsById(2);
        logger.info(tags.getTagId() + "");
    }
    @Test
    public void testQueryTags(){
        List<Tags> tagsList = tagsMapper.queryTags();
        logger.info("tagsList: {}", tagsList.toString());
    }
    @Test
    public void testUpdateTags(){
        Tags tags = new Tags();
        tags.setTagId(8);
        tags.setTagName("javajava");
        int effectedNum = tagsMapper.updateTags(tags);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void testDeleteTags(){

        int effectedNum = tagsMapper.deleteTags(8);
        Assert.assertEquals(1,effectedNum);
    }

}