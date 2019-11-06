package com.scitc.blog.web.admin;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Article;
import com.scitc.blog.model.Comment;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.ArticleService;
import com.scitc.blog.service.CommentService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manage")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public Result<Map<String, Object>> Comments(@RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize,
                                                HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        log.info("--------------------------来了");
        Map<String, Object> map = new HashMap<>();
        if (currentUser.getUserType() == 0) {
            //管理员显示所有文章


            OperationExecution<Comment> op = commentService.getCommentList(new Comment(), pageIndex, pageSize);
            map.put("commentList", op.getDataList());
            map.put("count", op.getCount());
            return ResultUtils.success(OperationEnums.SUCCESS, map);
        }
        //用户只显示自己文章下面的评论
        //先查询 该用户有哪些文章，再通过文章id去查询评论列表
        //模拟分页
        Article tempArticle = new Article();
        tempArticle.setUserInfo(currentUser);
        OperationExecution<Article> op = articleService.getArticleList(tempArticle, 0, 1000);
        List<Article> articleList = op.getDataList();

        log.info("articleList:{}", articleList);
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);

            Comment comment = new Comment();
            comment.setArticle(article);
            OperationExecution<Comment> op2 = commentService.getCommentList(comment, 0, 1000);

            log.info("op2.getDataList():{}", op2.getDataList());
            if (op2.getDataList().size() > 0) {
                for (int x = 0; x < op2.getDataList().size(); x++) {
                    commentList.add(op2.getDataList().get(x));
                }

                log.info("for结束：commentList:{}" +  commentList);

            }
        }

        //list模拟分页
        int start = (pageIndex - 1) * pageSize;
        int end = pageIndex * pageSize;
        if (end > commentList.size()) {
            end = commentList.size();
        }
        List<Comment> lastCommentList = new ArrayList<>();


        for (int i = start; i < end; i++) {
            lastCommentList.add(commentList.get(i));
        }

        log.info("commentList：{}",commentList);
        log.info("lastCommentList：{}",lastCommentList);

        map.put("commentList", lastCommentList);
        map.put("count", commentList.size());


        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    //删除
@DeleteMapping("/comment/{commentId}")
public Result deleteComment(@PathVariable("commentId") Integer commentId, HttpServletRequest request) {
    commentService.deleteComment(commentId);
    return ResultUtils.success(OperationEnums.DELETE_SUCCESS);
}
}
