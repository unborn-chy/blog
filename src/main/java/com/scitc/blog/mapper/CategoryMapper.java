package com.scitc.blog.mapper;

import com.scitc.blog.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    /**
     * 全部分类
     */
    List<Category> queryCategory(@Param("category") Category category);
    /**
     * 添加
     */
    int insertCategory(Category category);
    /**
     * 修改
     */
    int updateCategory(Category category);
    /**
     * 根据categoryId查询
     */
    Category queryCategoryById(@Param("categoryId") Integer categoryId,@Param("categoryName") String categoryName);

    /**
     * 删除
     */
    int deleteCategory(Integer categoryId);

    /***
     * 查询总数
     */
    int queryCategoryCount(@Param("category") Category category);
}
