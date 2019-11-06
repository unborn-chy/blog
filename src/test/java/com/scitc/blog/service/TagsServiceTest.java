package com.scitc.blog.service;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Tags;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagsServiceTest {

    private final static Logger log = LoggerFactory.getLogger(TagsServiceTest.class);
    @Autowired
    private TagsService tagsService;
    @Test
    public void testGetTagsList(){
        OperationExecution op  = tagsService.getTagsList();
        log.info("tagsList",op.getDataList());
    }

    @Test
    public void testAddTagsList(){
        Tags tags = new Tags();
        tags.setTagName("123123");
        OperationExecution oe = tagsService.insertTags(tags);
        Assert.assertEquals(OperationEnums.SUCCESS.getState(),oe.getState());
    }

    @Test
    public void testUpdateTags(){
        Tags tags = new Tags();

//        tags.set
        tags.setTagName("123321");
        OperationExecution oe = tagsService.updateTags(tags);
        Assert.assertEquals(OperationEnums.SUCCESS.getState(),oe.getState());
    }

    @Test
    public void testQueryTagsById(){
        OperationExecution oe = tagsService.getTagsById(9);
        Assert.assertEquals(OperationEnums.SUCCESS.getState(),oe.getState());
    }
    @Test
    public void testDeleteTagsById(){
        OperationExecution oe = tagsService.deleteTags(9);
        Assert.assertEquals(OperationEnums.SUCCESS.getState(),oe.getState());
    }

}
