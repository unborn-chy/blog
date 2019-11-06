package com.scitc.blog.web.admin;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Category;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.CategoryService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getcategorparent")
    public Result<Map<String, Object>> getCategoryParent() {
        Map<String, Object> map = new HashMap<>();
        //parentId为0的，父类别
        OperationExecution oe = categoryService.getCategoryList(new Category());
        map.put("categoryParentList", oe.getDataList());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

@PostMapping("/category")
public Result addCategory(@RequestParam("categoryName") String categoryName,
                          @RequestParam("parentId") Integer parentId,
                          HttpServletRequest request) {
    logger.info("-----进来了");
    UserInfo currentUser = CommonUtil.getUserInfo(request);

    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    Category category = new Category();
    category.setCategoryName(categoryName);
    Category parentIdCategory = new Category();
    parentIdCategory.setCategoryId(parentId);
    category.setParentId(parentIdCategory);

    OperationExecution oe = categoryService.addCategory(category);
    return ResultUtils.success(oe.getStateInfo());
}

@GetMapping("/categories")
public Result<Map<String, Object>> getCategories() {
//        Map<String, Object> map = new HashMap<>();
//        OperationExecution oe = categoryService.getCategoryList(null);
//        map.put("categoryList", oe.getDataList());
//        map.put("count", oe.getCount());
//        return ResultUtils.success(OperationEnums.SUCCESS, map);

    Map<String, Object> map = new HashMap<>();
    Category parent = new Category();
    OperationExecution oeParent = categoryService.getCategoryList(parent);
    map.put("categoryParent", oeParent.getDataList());

    Category category = new Category();
    category.setParentId(parent);
    OperationExecution oe = categoryService.getCategoryList(category);
    map.put("categoryList", oe.getDataList());
    int count = oeParent.getDataList().size() + oe.getDataList().size();
    map.put("count", count);
    return ResultUtils.success(OperationEnums.SUCCESS, map);
}

    @GetMapping("/category/{categoryId}")
    public Result<Map<String, Object>> getCategories(@PathVariable("categoryId") Integer categoryId, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();

        OperationExecution oeCategory = categoryService.getCategoryById(categoryId,null);
        OperationExecution ceCategoryList = categoryService.getCategoryList(new Category());
        map.put("category", oeCategory.getData());
        map.put("categoryParentList", ceCategoryList.getDataList());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    @PutMapping("/category/{categoryId}")
    public Result updateCategory(@PathVariable("categoryId") Integer categoryId,
                                 @RequestParam("categoryName") String categoryName,
                                 @RequestParam("parentId") Integer parentId,
                                 HttpServletRequest request) {
        logger.info("-----进来了");
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        Category parentIdCategory = new Category();
        parentIdCategory.setCategoryId(parentId);
        category.setParentId(parentIdCategory);

        OperationExecution oe = categoryService.updateCategory(category);
        return ResultUtils.success(oe.getStateInfo());
    }

@DeleteMapping("/category/{categoryId}")
public Result deleteCategory(@PathVariable("categoryId") Integer categoryId, HttpServletRequest request) {
    logger.info("进来- - - -");
    UserInfo currentUser = CommonUtil.getUserInfo(request);
    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    OperationExecution oe = categoryService.deleteCategory(categoryId);
    return ResultUtils.success(oe.getStateInfo());
}

}
