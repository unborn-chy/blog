package com.scitc.blog.web.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scitc.blog.dto.*;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Article;
import com.scitc.blog.model.Category;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.ArticleService;
import com.scitc.blog.service.CategoryService;
import com.scitc.blog.service.TagsService;
import com.scitc.blog.utils.HttpServletRequestUtil;
import com.scitc.blog.utils.ResultUtils;
import com.scitc.blog.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class ArticleController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagsService tagsService;

    @Autowired
    private ArticleService articleService;
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    /**
     * 设置通过
     *
     * @return
     */
    @PutMapping("/articles/{articleId}")
    public Result updateArticleOther(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
        log.info("进来了- - - - - ");
        //1. 接收参数
        UserInfo userInfo = CommonUtil.getUserInfo(request);
        if (userInfo.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Article article = new Article();
        article.setArticleId(articleId);
        article.setArticleState(0);
        try {
            articleService.updateArticle(article, null);
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }
        return ResultUtils.success(OperationEnums.SUCCESS);
    }

    //删除
@DeleteMapping("/article/{articleId}")
public Result deleteArticle(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
    log.info("------------ 进来了  ----------------");

    log.info("articleId{}:", articleId);
    UserInfo userInfo = CommonUtil.getUserInfo(request);
    try {
        Article article = articleService.getByArticleId(articleId);
        Integer currentUserId = article.getUserInfo().getUserId();
        if (userInfo.getUserId() == currentUserId || userInfo.getUserType() == 0) {
            articleService.deleteArticleById(articleId);
            return ResultUtils.success(OperationEnums.DELETE_SUCCESS);
        } else {
            return ResultUtils.error(OperationEnums.ARTICLE_USER_NOT_YOU);
        }
    } catch (Exception e) {
        throw new BlogException(e.getMessage());
    }
}


@GetMapping("/articles")
public Result<Map<String, Object>> articles(@RequestParam("pageIndex") int pageIndex,
                                            @RequestParam("pageSize") int pageSize,
                                            @RequestParam("status") int articleState,
                                            HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    UserInfo userInfo = CommonUtil.getUserInfo(request);
    log.info("userInfo:", userInfo);
    Article article = new Article();
    Article OtherArticle = new Article();
    //不是管理员就显示用户下的文章  无user条件显示所有
    if (userInfo.getUserType() != 0) {
        article.setUserInfo(userInfo);
        OtherArticle.setUserInfo(userInfo);
    }
    article.setArticleState(articleState);
    int OtherState = 0;
    if (articleState == 0) {
        OtherState = 1;
    }
    OtherArticle.setArticleState(OtherState);
    try {
        OperationExecution oe = articleService.getArticleList(article, pageIndex, pageSize);
        OperationExecution otherOe = articleService.getArticleList(OtherArticle, pageIndex, pageSize);
        map.put("articleList", oe.getDataList());
        map.put("count", oe.getCount());
        map.put("otherCount", otherOe.getCount());
        map.put("userType", userInfo.getUserType());
        return ResultUtils.success(OperationEnums.SUCCESS, map);

    } catch (Exception e) {
        return ResultUtils.error(OperationEnums.ARTICLE_LIST_ERROR);
    }
}


    @GetMapping("/article/{articleId}")
    public Result<Map<String, Object>> getArticleById(@PathVariable("articleId") Integer articleId,
                                                      HttpServletRequest request) {
        //判断一下文章 是否属于当前用户
        UserInfo userInfo = CommonUtil.getUserInfo(request);


        Map<String, Object> map = new HashMap<>();
        try {
            Article article = articleService.getByArticleId(articleId);
            Integer currentUserId = article.getUserInfo().getUserId();
            //是当前用户或者是管理员就可操作
            if (userInfo.getUserId().equals(currentUserId) || userInfo.getUserType() == 0) {

                Category tempCategory=  new Category();
                tempCategory.setParentId(new Category());

                OperationExecution oeCate = categoryService.getCategoryList(tempCategory);
                OperationExecution op = tagsService.getTagsList();
                map.put("article", article);
                map.put("categoryList", oeCate.getDataList());
                map.put("tagsList", op.getDataList());
            } else {
                return ResultUtils.error(OperationEnums.ARTICLE_USER_NOT_YOU);
            }

        } catch (Exception e) {
            throw new BlogException(OperationEnums.NULL_INFO);
        }

        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    @GetMapping("/getcategorytagslist")
    public Result<Map<String, Object>> getCategoryTagsList(HttpServletRequest request) {
        log.info("进来显示类别标签了-----------------------------");
        Map<String, Object> map = new HashMap<>();
        try {
            Category tempCategory=  new Category();
            tempCategory.setParentId(new Category());

            OperationExecution oeCate = categoryService.getCategoryList(tempCategory);
            OperationExecution op = tagsService.getTagsList();
            map.put("categoryList", oeCate.getDataList());
            map.put("tagsList", op.getDataList());
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

//添加
@PostMapping("/article")
public Result<Article> addArticle(@RequestParam(value = "articleImg") MultipartFile articleImg,
                                  HttpServletRequest request) {
    log.info("------------ 进来了----------------");

    //1. 接收参数
    String ArticleStr = HttpServletRequestUtil.getString(request, "articleStr");
    log.info("ArticleStr:{}", ArticleStr);
    ObjectMapper mapper = new ObjectMapper();
    Article article = null;

    try {
        article = mapper.readValue(ArticleStr, Article.class);
        log.info("mapper --- article:{}", article);
    } catch (Exception e) {
        throw new BlogException(e.getMessage());
    }
    //标签
    String[] ArticleTagsArray = request.getParameterValues("articleTags");
    String articleTags = CommonUtil.getStingTags(ArticleTagsArray);
    log.info("articleTags:{}", articleTags);

    if (articleTags == null) {
        return ResultUtils.error(OperationEnums.TAG_NOT_NULL);
    }
    log.info("@: articleImg {}", articleImg.getOriginalFilename());
    //项目名称
    // String ContentPath = CommonUtil.getContextPath(request);
    //2.添加文章
    if (article != null && articleTags != null && articleImg != null) {
        //登录获取
        UserInfo userInfo = CommonUtil.getUserInfo(request);
        article.setUserInfo(userInfo);
        article.setArticleTags(articleTags);
        article.setArticleState(userInfo.getUserType());
        OperationExecution oe = null;
        try {
            ImageHolder imageHolder = new ImageHolder(articleImg.getOriginalFilename(), articleImg.getInputStream());
            oe = articleService.addArticle(article, imageHolder);
            log.info("ae:State" + oe.getState() + "  ae:info" + oe.getStateInfo());
            if (userInfo.getUserType() == 0) {
                return ResultUtils.success(OperationEnums.INNER_SUCCESS);
            } else {
                return ResultUtils.success(OperationEnums.INNER_SUCCESS_CHECK);
            }
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }
    } else {
        return ResultUtils.error(OperationEnums.INPUT_COMPLETE_INFO);
    }
}

    //修改
    @PutMapping("/article/{articleId}")
    public Result<Article> updateArticle(@PathVariable("articleId") Integer articleId,
                                         @RequestParam(value = "articleImg", required = false) MultipartFile articleImg,
                                         HttpServletRequest request) {
        log.info("------------ 进来了修改----------------" + articleId);

        //先判断是否属于该用户
        UserInfo userInfo = CommonUtil.getUserInfo(request);

        Article tempArticle = articleService.getByArticleId(articleId);
        Integer currentUserId = tempArticle.getUserInfo().getUserId();
        //是当前用户或者是管理员就可操作
        if (!userInfo.getUserId().equals(currentUserId) && userInfo.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.ARTICLE_USER_NOT_YOU);
        }

        //1. 接收参数
        String ArticleStr = HttpServletRequestUtil.getString(request, "articleStr");
        log.info("ArticleStr:{}", ArticleStr);
        ObjectMapper mapper = new ObjectMapper();
        Article article = null;
        try {
            article = mapper.readValue(ArticleStr, Article.class);
            log.info("mapper --- article:{}", article);
        } catch (Exception e) {
            throw new BlogException(e.getMessage());
        }
        String[] ArticleTagsArray = request.getParameterValues("articleTags");
        String articleTags = CommonUtil.getStingTags(ArticleTagsArray);
        log.info("articleTags:{}", articleTags);

        if (articleTags == null) {
            return ResultUtils.error(OperationEnums.TAG_NOT_NULL);
        }
        //获取图片 因为图片可能会没有，所以用变量来接受判断
        MultipartFile getArticleImg = articleImg;
        //2.修改文章
        if (article != null && articleTags != null) {
            //登录之后重session中获取
            article.setUserInfo(userInfo);
            article.setArticleTags(articleTags);
            //更新文章编辑时间
            article.setLastEditTime(new Date());
            OperationExecution oe = null;

            ImageHolder imageHolder = null;
            try {
                if (getArticleImg != null && !"".equals(getArticleImg)) {
                    imageHolder = new ImageHolder(getArticleImg.getOriginalFilename(), getArticleImg.getInputStream());
                    log.info("imageHolder " + imageHolder.getImageName() + " --" + imageHolder.getImage());
                }
                oe = articleService.updateArticle(article, imageHolder);
                log.info("ae:State" + oe.getState() + "  ae:info" + oe.getStateInfo());
                if (userInfo.getUserType() == 0) {
                    return ResultUtils.success(oe.getStateInfo());
                } else {
                    return ResultUtils.success(OperationEnums.INNER_SUCCESS_CHECK);
                }
            } catch (IOException e) {
                throw new BlogException(e.getMessage());
            }
        } else {
            return ResultUtils.error(OperationEnums.INPUT_COMPLETE_INFO);
        }

    }
}
