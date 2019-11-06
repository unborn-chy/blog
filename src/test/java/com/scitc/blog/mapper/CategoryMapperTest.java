package com.scitc.blog.mapper;

import com.scitc.blog.model.Category;
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
public class CategoryMapperTest {
    @Autowired
    CategoryMapper categoryMapper;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void queryCategoryListTest() {
        Category category = new Category();
//        Category parentIdCategory = new Category();
        category.setParentId(new Category());

        List<Category> categoryList = categoryMapper.queryCategory(category);

        int count = categoryMapper.queryCategoryCount(category);
        logger.info(categoryList.toString());
       // logger.info("count",count);
        System.out.println("count:" + count);
    }

    @Test
    public void testInsertCategory() {

        Category category = new Category();
        category.setCategoryName("图片分享2");
        category.setCreateTime(new Date());

        Category categoryParent = new Category();
        categoryParent.setCategoryId(4);

        category.setParentId(categoryParent);
        int effectedNum = categoryMapper.insertCategory(category);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        category.setCategoryId(11);
        category.setCategoryName("图片分享33");
        Category categoryParentId = new Category();
        categoryParentId.setCategoryId(4);
        category.setParentId(categoryParentId);

        logger.info("category {}", category);
        int effectedNum = categoryMapper.updateCategory(category);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testDeleteCategory() {

        int effectedNum = categoryMapper.deleteCategory(11);
        Assert.assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryCategoryById() {

        Category category = categoryMapper.queryCategoryById(null,"Java开发");
//        Assert.assertEquals(1, effectedNum);
        logger.info("category{} ",category);
    }

    @Test
    public void testQueryCategoryByParentId() {
        Category categoryParent = new Category();
        categoryParent.setCategoryId(4);
        Category category = new Category();
        category.setParentId(categoryParent);
        List<Category> categoryList = categoryMapper.queryCategory(category);

        logger.info("categoryList{}:",categoryList);
    }


}