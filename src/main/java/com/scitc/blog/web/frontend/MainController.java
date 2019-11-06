package com.scitc.blog.web.frontend;

import cn.hutool.http.HtmlUtil;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.*;
import com.scitc.blog.service.*;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.HttpServletRequestUtil;
import com.scitc.blog.utils.ResultUtils;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequestMapping("/frontend")
@RestController
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AdviceService adviceService;

    //防止重复点赞
//    private String userLike = "";
    private ArrayList<String> userLikeList = new ArrayList<>();


    @GetMapping("/categorysidebar")
    public Result<Map<String, Object>> getCategorySidebar() {
        Map<String, Object> map = new HashMap<>();
        //全部父类别
        Category category = new Category();
        OperationExecution operationExecution = categoryService.getCategoryList(category);
        map.put("categoryListParent", operationExecution.getDataList());
        //全部子类
        Category Parent = new Category();
        category.setParentId(Parent);
        operationExecution = categoryService.getCategoryList(category);
        map.put("categoryList", operationExecution.getDataList());
        //标签
        operationExecution = tagsService.getTagsList();
        map.put("tagsList", operationExecution.getDataList());
        //网站概况

        //1.文章总数
        OperationExecution<Article> oeArticle = articleService.getArticleList(new Article(), 0, 1000);
        int viewsCount = 0;
        Date lastEditTime = null;
        for (int i = 0; i < oeArticle.getDataList().size(); i++) {
            viewsCount += oeArticle.getDataList().get(i).getArticleViews();
            lastEditTime = oeArticle.getDataList().get(0).getCreateTime();

        }
        //最后编辑时间
        map.put("lastEditTime", lastEditTime);
        map.put("articleCount", oeArticle.getCount());
        //浏览总量
        map.put("viewsCount", viewsCount);
        //分类数量
        operationExecution = categoryService.getCategoryList(null);
        map.put("categoryCount", operationExecution.getCount());
        //标签数量
        operationExecution = tagsService.getTagsList();
        map.put("tagsCount", operationExecution.getCount());

        //近期评论
        operationExecution = commentService.getCommentList(new Comment(), 0, 10);
        map.put("commentList", operationExecution.getDataList());
        map.put("commentCount", operationExecution.getCount());

        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    @GetMapping("/getallinfo")
    public Result<Map<String, Object>> getAllInfo(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        //公告
        List<Advice> adviceList = adviceService.getAdviceList();
        map.put("adviceList", adviceList);
        //轮播
        OperationExecution operationExecution = headLineService.getHeadLineList();
        map.put("headLineList", operationExecution.getDataList());
        //标签
        //文章 把里面的标签给去掉
        Article article = new Article();
        article.setArticleState(0);
        operationExecution = articleService.getArticleList(article, pageIndex, pageSize);
        List<Article> articleList = operationExecution.getDataList();
        map.put("articleList", articleList);
        map.put("articleCount", operationExecution.getCount());

        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    //点击标签回显文章

    @GetMapping("/tag/{son}")
    public Result<Map<String, Object>> getTagSon(@PathVariable("son") String son, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Article article = new Article();
        article.setArticleTags(son);
        article.setArticleState(0);
        OperationExecution oe = articleService.getArticleList(article, pageIndex, pageSize);
        if (oe.getCount() == 0) {
            return ResultUtils.error(OperationEnums.NULL_INFO);
        } else {
            map.put("articleList", oe.getDataList());
            map.put("articleCount", oe.getCount());
            return ResultUtils.success(OperationEnums.SUCCESS, map);
        }
    }

    @GetMapping("/category/{son}")
    public Result<Map<String, Object>> getCategorySon(@PathVariable("son") String son, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        logger.info("son:{}", son);

        //查出来类别信息，主要是去获取id
        OperationExecution oe = categoryService.getCategoryById(null, son);
        Category getCurrentCategory = (Category) oe.getData();
        logger.info("getCurrentCategory:{}", getCurrentCategory);

        //如果传过来的是子类别
        Article article = new Article();
        article.setCategory(getCurrentCategory);
        article.setArticleState(0);
        oe = articleService.getArticleList(article, pageIndex, pageSize);
        if (oe.getDataList().size() > 0) {
            map.put("articleList", oe.getDataList());
            map.put("articleCount", oe.getCount());
            logger.info("oe.getDataList(){}", oe.getDataList());
            return ResultUtils.success(OperationEnums.SUCCESS, map);
        } else {
            //如果是点击的父类别
            Category category = new Category();
            category.setParentId(getCurrentCategory);
            oe = categoryService.getCategoryList(category);
            List<Category> categoryList = oe.getDataList();
            logger.info("------------------------------");
            logger.info("oe.categoryList():{}", categoryList);
            List<Article> articleList = new ArrayList<>();
            //遍历获取到的子类别
            for (int i = 0; i < categoryList.size(); i++) {
                int categoryId = categoryList.get(i).getCategoryId();
                logger.info("categoryId:{}", categoryId);
                Article forArticle = new Article();
                getCurrentCategory.setCategoryId(categoryId);
                forArticle.setCategory(getCurrentCategory);
                forArticle.setArticleState(0);
                //这是假分页获取，目的是获取了某子类别下面的所有文章
                OperationExecution<Article> oeArticle = articleService.getArticleList(forArticle, 1, 1000);
                if (oeArticle.getDataList().size() > 0) {
                    List<Article> ifDataArticleList = oeArticle.getDataList();
                    for (int x = 0; x < ifDataArticleList.size(); x++) {
                        //所有查出来的article列表添加到list中
                        articleList.add(ifDataArticleList.get(x));
                    }
                }
            }
            if (articleList.size() > 0) {
                //list模拟分页
                int start = (pageIndex - 1) * pageSize;
                int end = pageIndex * pageSize;
                if (end > articleList.size()) {
                    end = articleList.size();
                }
                List<Article> lastArticleList = new ArrayList<>();
                for (int i = start; i < end; i++) {
                    lastArticleList.add(articleList.get(i));
                }
                map.put("articleList", lastArticleList);
                map.put("articleCount", articleList.size());
                return ResultUtils.success(OperationEnums.SUCCESS, map);
            } else {
                return ResultUtils.error(OperationEnums.NULL_INFO);
            }
        }
    }

@PostMapping("/keyword")
public Result<Map<String, Object>> byKeyword(@RequestParam("keyword") String keyword) {
    logger.info("keyword:{}", keyword);
    Map<String, Object> map = new HashMap<>();
    Article article = new Article();
    article.setArticleTitle(keyword);
    article.setArticleState(0);
    OperationExecution oe = articleService.getArticleList(article, 1, 1000);
    if (oe.getDataList().size() > 0) {
        map.put("articleList", oe.getDataList());
        map.put("articleCount", oe.getCount());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    } else {
        return ResultUtils.error(OperationEnums.NULL_INFO);
    }
}


    //浏览量


    @GetMapping("/article/{articleId}")
    public Result<Map<String, Object>> getArticleById(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getByArticleId(articleId);

        //浏览量
        Integer currentView = article.getArticleViews();
        Integer newView = currentView + 1;
        article.setArticleViews(newView);
        articleService.updateArticle(article, null);

        logger.info("article{}", article);
        Map<String, Object> map = new HashMap<>();
        map.put("article", article);
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    //点赞
    @PutMapping("/article/{articleId}")
    public Result likeCount(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
        UserInfo userInfo = CommonUtil.getUserInfo(request);
        if (userInfo == null) {
            return ResultUtils.error(OperationEnums.NO_LOGIN);
        }

        String userLike = userInfo.getUsername() + articleId;
        if (userLikeList.size() > 0) {
            for (String like : userLikeList) {
                if (like.equals(userLike)) {
                    return ResultUtils.error(OperationEnums.REPEAT_LIKE_COUNT);
                }
            }
        }
        userLikeList.add(userLike);
        //先把原来的查出来再+1
        Article article = articleService.getByArticleId(articleId);
        Integer oldLikeCount = article.getArticleLikeCount();
        Integer newLikeCount = oldLikeCount + 1;

        article.setArticleLikeCount(newLikeCount);
        articleService.updateArticle(article, null);

        return ResultUtils.success(OperationEnums.LIKE_COUNT_SUCCESS, article);
    }


    //显示所有评论
    @GetMapping("/comment/{articleId}")
    public Result<Map<String, Object>> allArticleComment(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Comment tempComment = new Comment();

        Article article = new Article();
        article.setArticleId(articleId);
        tempComment.setArticle(article);

        OperationExecution<Comment> op = commentService.getCommentList(tempComment, 1, 1000);
        List<Comment> commentList = op.getDataList();
        //分开拿到 评论和回复
        List<Comment> commentFromList = new ArrayList<>();
        List<Comment> commentToList = new ArrayList<>();

        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getParentId() == null) {
                commentFromList.add(commentList.get(i));
            } else {
                commentToList.add(commentList.get(i));
            }
        }
        map.put("commentFromList", commentFromList);
        map.put("commentToList", commentToList);
        map.put("commentCount", op.getCount());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    //添加评论
    @PostMapping("/comment/{articleId}")
    public Result addComment(@PathVariable("articleId") Integer articleId,
                             @RequestParam("content") String content, HttpServletRequest request) {
        logger.info("content:{}", content);
        UserInfo userInfo = CommonUtil.getUserInfo(request);
        logger.info("userInfo:{}", userInfo);
        if (userInfo == null) {
            return ResultUtils.error(OperationEnums.NO_LOGIN);
        }
        //只有作者可回复
        Article currentArticle = articleService.getByArticleId(articleId);
        logger.info("currentArticle:{}", currentArticle);
        Integer currentArticleUserId = currentArticle.getUserInfo().getUserId();

        Comment comment = new Comment();
        comment.setUserInfoFrom(userInfo);
        comment.setCommentContent(content);

        Article article = new Article();
        article.setArticleId(articleId);

        comment.setArticle(article);
        comment.setCreateTime(new Date());
        String username = HttpServletRequestUtil.getString(request, "username");
        int commentId = HttpServletRequestUtil.getInt(request, "commentId");
        logger.info("username:{}", username);
        logger.info("commentId:{}", commentId);

        if (username != null && commentId != -1) {
            UserInfo tempUserInfo = userInfoService.findByUserInfo(null, username, null);

            logger.info("userInfo.getUserId():{}", userInfo.getUserId());
            logger.info("currentArticleUserId:{}", currentArticleUserId);

            if (!userInfo.getUserId().equals(currentArticleUserId)) {
                return ResultUtils.error(OperationEnums.ONLY_AUTHOR_REPLAY);
            }
            logger.info("tempUserInfo:{}", tempUserInfo);
            comment.setToId(tempUserInfo.getUserId());
            comment.setParentId(commentId);
        }
        commentService.addComment(comment);
        return ResultUtils.success(OperationEnums.REPLAY_SUCCESS);
    }

}
