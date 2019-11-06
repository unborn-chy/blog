package com.scitc.blog.service;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Article;

public interface ArticleService {

    /**
     * 添加文章
     * @return
     * @throws BlogException
     */
    public OperationExecution addArticle(Article article, ImageHolder imageHolder) throws BlogException;

    /**
     * 根据ArticleId查询
     * @param articleId
     * @return
     * @throws BlogException
     */
    public Article getByArticleId(Integer articleId)throws BlogException;

    /**
     * 更新文章
     * @param article
     * @param imageHolder
     * @param
     * @return
     * @throws BlogException
     */
    public OperationExecution updateArticle(Article article, ImageHolder imageHolder) throws BlogException;

    /**
     * 查询列表
     * @param article
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public OperationExecution getArticleList(Article article,int pageIndex,int pageSize);

    public int deleteArticleById(Integer articleId);

}
