package com.scitc.blog.service.impl;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.CategoryMapper;
import com.scitc.blog.model.Category;
import com.scitc.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public OperationExecution getCategoryList(Category category) throws BlogException {
        OperationExecution oe = new OperationExecution();
        List<Category> categoryList = categoryMapper.queryCategory(category);
        if(categoryList==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        int count = categoryMapper.queryCategoryCount(category);
        oe.setDataList(categoryList);
        oe.setCount(count);
        return oe;
    }

    @Override
    public OperationExecution getCategoryById(Integer categoryId,String categoryIdName) throws BlogException {
        Category category= categoryMapper.queryCategoryById(categoryId,categoryIdName);
        if(category==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        return new OperationExecution(OperationEnums.SUCCESS,category);
    }

    @Override
    @Transactional
    public OperationExecution addCategory(Category category) throws BlogException {
            category.setCreateTime(new Date());
            int effectedNum = categoryMapper.insertCategory(category);
            if (effectedNum <= 0) {
                throw new BlogException(OperationEnums.INNER_ERROR);
            }
            return new OperationExecution(OperationEnums.INNER_SUCCESS);


    }

    @Override
    @Transactional
    public OperationExecution updateCategory(Category category) throws BlogException {
        if (category == null || category.getCategoryId() == null) {
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        int effectedNum = categoryMapper.updateCategory(category);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.UPDATE_ERROR);
        }
        return new OperationExecution(OperationEnums.UPDATE_SUCCESS);
    }

    @Override
    @Transactional
    public OperationExecution deleteCategory(Integer categoryId) throws BlogException {
        int effectedNum = categoryMapper.deleteCategory(categoryId);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.DELETE_ERROR);
        }
        return new OperationExecution(OperationEnums.DELETE_SUCCESS);
    }
}
