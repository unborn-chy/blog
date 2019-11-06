package com.scitc.blog.mapper;

import com.scitc.blog.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    int insertArticle(Article article);

    /**
     * 更新文章
     *
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 根据articleId查询
     *
     * @param articleId
     * @return
     */
    Article queryByArticleId(Integer articleId);

    List<Article> queryArticleList(@Param("article") Article article, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    int queryArticleCount(@Param("article") Article article);
    //删除文章
    int deleteArticleById(Integer articleId);

}
