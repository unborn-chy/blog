package com.scitc.blog.service;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Category;

public interface CategoryService {
    OperationExecution getCategoryList(Category category) throws BlogException;

    OperationExecution getCategoryById(Integer categoryId,String categoryIdName) throws BlogException;

    OperationExecution addCategory(Category category) throws BlogException;

    OperationExecution updateCategory(Category category) throws BlogException;

    OperationExecution deleteCategory(Integer categoryId) throws BlogException;
}
