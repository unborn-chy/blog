package com.scitc.blog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.ArticleMapper;
import com.scitc.blog.model.Article;
import com.scitc.blog.service.ArticleService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ImageUtil;
import com.scitc.blog.utils.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Value(value = "${server.servlet.context-path}")
    private String contextPath;


    @Override
    @Transactional
    public int deleteArticleById(Integer articleId) {
        //到时候需要改  不是真的删除只是把状态改变了
        int effectedNum = articleMapper.deleteArticleById(articleId);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.REGISTER_ERROR);
        } else {
            return effectedNum;
        }

    }

    @Override
    public Article getByArticleId(Integer articleId) throws BlogException {

        Article article = articleMapper.queryByArticleId(articleId);

        if (article == null) {
            throw new BlogException(OperationEnums.NULL_INFO);
        } else {
            return article;
        }


    }

    @Override
    @Transactional
    public OperationExecution updateArticle(Article article, ImageHolder imageHolder) throws BlogException {

        if (article.getArticleId() == null) {
            throw new BlogException(OperationEnums.NULL_ID);
        }
        try {
            //处理图片
            if (imageHolder != null && imageHolder.getImage() != null && imageHolder.getImageName() != null && !"".equals(imageHolder.getImageName())) {
                log.info("imageHolder - {}", imageHolder.getImageName());
                Article tempArticle = articleMapper.queryByArticleId(article.getArticleId());
                if (tempArticle.getArticleImg() != null) {
                    ImageUtil.deleteFileOrPath(tempArticle.getArticleImg());
                }
                log.info("contextPath 23:{}", contextPath);
                addArticleImg(article, imageHolder, contextPath);
            }
            int effectedNum = articleMapper.updateArticle(article);
            if (effectedNum <= 0) {
                throw new BlogException(OperationEnums.UPDATE_ERROR);
            } else {
                return new OperationExecution(OperationEnums.UPDATE_SUCCESS, article);
            }

        } catch (Exception e) {
            throw new BlogException("updateArticle error" + e.getMessage());
        }
    }

    @Override
    public OperationExecution getArticleList(Article article, int pageIndex, int pageSize) {
        OperationExecution oe;
        try {
            int rowIndex = CommonUtil.getRowIndex(pageIndex, pageSize);
            List<Article> articleList = articleMapper.queryArticleList(article, rowIndex, pageSize);
            //去掉 html标签
            for (int i = 0; i < articleList.size(); i++) {
                String lodContent = articleList.get(i).getArticleContent();
                String newContent = HtmlUtil.cleanHtmlTag(lodContent);
                articleList.get(i).setArticleContent(newContent);
            }
            int count = articleMapper.queryArticleCount(article);
            oe = new OperationExecution();
            oe.setDataList(articleList);
            oe.setCount(count);
            oe.setState(OperationEnums.SUCCESS.getState());
        } catch (Exception e) {
            throw new BlogException(OperationEnums.ARTICLE_LIST_ERROR);
        }
        return oe;
    }


    @Override
    @Transactional
    public OperationExecution addArticle(Article article, ImageHolder imageHolder) throws BlogException {

        try {
            //赋初始值
            article.setCreateTime(new Date());
            article.setLastEditTime(new Date());
            //添加文章
            int effectedNum = articleMapper.insertArticle(article);
            if (effectedNum <= 0) {
                throw new BlogException(OperationEnums.INNER_ERROR);
            } else {
                if (imageHolder.getImage() != null) {
                    try {
                        //存储图片
                        addArticleImg(article, imageHolder, contextPath);
                    } catch (BlogException e) {
                        throw new BlogException(OperationEnums.ADDIMAGE_ERROR);
                    }
                    //更新文章缩略图
                    effectedNum = articleMapper.updateArticle(article);
                    if (effectedNum <= 0) {
                        throw new BlogException(OperationEnums.THUMBNAIL_ERROR);
                    }
                }
            }
        } catch (Exception e) {
            throw new BlogException("addArticle error" + e.getMessage());
        }
        return new OperationExecution(OperationEnums.INNER_SUCCESS, article);
    }


    private void addArticleImg(Article article, ImageHolder imageHolder, String contextPath) {
        String dest = PathUtil.getImagePath(article.getUserInfo().getUserId());
        log.info("ArticleServiceImpl  dest :{}", dest);
        String relativeAddr = ImageUtil.addImgAddr(dest, imageHolder);
        article.setArticleImg(contextPath + relativeAddr);
        log.info("存入数据库 ArticleImg", contextPath + relativeAddr);
    }
}
