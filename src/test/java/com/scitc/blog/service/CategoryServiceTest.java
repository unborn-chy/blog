package com.scitc.blog.service;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(CategoryServiceTest.class);
    @Autowired
    private CategoryService categoryService;

    @Test
    public void testGetCategory() {
        OperationExecution ce = categoryService.getCategoryList(new Category());
        //Assert.assertEquals(3,effectedNum);
        logger.info("categoryList{}" + ce.getDataList());
    }


    @Test
    public void testAddCategory() {
        Category category = new Category();
        category.setCategoryName("PHP");

        Category parentCategory = new Category();
        parentCategory.setCategoryId(0);

        category.setParentId(parentCategory);

        OperationExecution categoryExecution = categoryService.addCategory(category);
        Assert.assertEquals(OperationEnums.INNER_SUCCESS.getState(), categoryExecution.getState());
//        logger.info("categoryExecution {}",categoryExecution.getState());
//        logger.info("categoryExecution {}",categoryExecution.getState());

    }

    @Test
    public void getCategoryById() {
        OperationExecution categoryById = categoryService.getCategoryById(null,"Java开发");
        logger.info("categoryById {}", categoryById.getData());
    }

    @Test
    public void UpdateCategory() {
        Category category = new Category();
        category.setCategoryId(14);
        category.setCategoryName("PHP223");

        Category parentCategory = new Category();
        parentCategory.setCategoryId(0);

        category.setParentId(parentCategory);
        OperationExecution categoryExecution = categoryService.updateCategory(category);
        Assert.assertEquals(OperationEnums.UPDATE_SUCCESS.getState(), categoryExecution.getState());
    }

    @Test
    public void DeleteCategory() {
        OperationExecution categoryExecution = categoryService.deleteCategory(14);
        Assert.assertEquals(OperationEnums.DELETE_SUCCESS.getState(), categoryExecution.getState());
    }
}
