package com.scitc.blog.service.impl;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.HeadLineMapper;
import com.scitc.blog.model.HeadLine;
import com.scitc.blog.service.HeadLineService;
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
public class HeadLineServiceImpl implements HeadLineService {
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private HeadLineMapper headLineMapper;
    @Value(value = "${server.servlet.context-path}")
    private String contextPath;

    @Override
    @Transactional
    public OperationExecution addHeadLine(HeadLine headLine, ImageHolder imageHolder, Integer userId) throws BlogException {
        try {
            headLine.setCreateTime(new Date());
            int effectedNum = headLineMapper.insertHeadLine(headLine);
            if (effectedNum <= 0) {
                throw new BlogException(OperationEnums.INNER_ERROR);
            } else {
                if (imageHolder.getImage() != null && imageHolder.getImageName() != null) {
                    //处理图片
                    try {
                        AddHeadLineImg(headLine, imageHolder, userId);
                    } catch (Exception e) {
                        throw new BlogException(OperationEnums.ADDIMAGE_ERROR);
                    }
                    effectedNum = headLineMapper.updateHeadLine(headLine);
                } else {
                    headLine.setLineImg(imageHolder.getImageName());
                    effectedNum = headLineMapper.updateHeadLine(headLine);
                }
                if (effectedNum <= 0) {
                    throw new BlogException(OperationEnums.UPDATE_IMAGE_ERROR);
                } else {
                    return new OperationExecution(OperationEnums.INNER_SUCCESS);
                }
            }

        } catch (Exception e) {
            throw new BlogException("addHeadLine error" + e.getMessage());
        }
    }


    @Override
    @Transactional
    public OperationExecution updateHeadline(HeadLine headLine, ImageHolder imageHolder, Integer userId) throws BlogException {
        try {
            int effectedNum = headLineMapper.updateHeadLine(headLine);
            if (effectedNum <= 0) {
                throw new BlogException(OperationEnums.UPDATE_ERROR);
            } else {
                if (imageHolder.getImage() != null && imageHolder.getImageName() != null) {
                    //处理图片
                    try {
                        AddHeadLineImg(headLine, imageHolder, userId);
                    } catch (Exception e) {
                        throw new BlogException(OperationEnums.ADDIMAGE_ERROR);
                    }
                    effectedNum = headLineMapper.updateHeadLine(headLine);

                } else {
                    headLine.setLineImg(imageHolder.getImageName());
                    effectedNum = headLineMapper.updateHeadLine(headLine);
                }
                if (effectedNum <= 0) {
                    throw new BlogException(OperationEnums.UPDATE_IMAGE_ERROR);
                } else {
                    return new OperationExecution(OperationEnums.UPDATE_SUCCESS);
                }
            }

        } catch (Exception e) {
            throw new BlogException("addHeadLine error" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OperationExecution deleteHeadLine(Integer lineId) throws BlogException {
        HeadLine headLine = headLineMapper.queryHeadLineById(lineId);
        if(headLine==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        int effectedNum = headLineMapper.deleteHeadLine(lineId);
        if(effectedNum<=0){
            throw new BlogException(OperationEnums.DELETE_ERROR);
        }
        return new OperationExecution(OperationEnums.DELETE_SUCCESS);
    }

    @Override
    public OperationExecution getHeadLine(Integer lineId) throws BlogException {
        HeadLine headLine = headLineMapper.queryHeadLineById(lineId);
        if(headLine==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        return new OperationExecution(OperationEnums.SUCCESS, headLine);
    }

    @Override
    public OperationExecution getHeadLineList() throws BlogException {
        OperationExecution oe = new OperationExecution();
        List<HeadLine> headLineList = headLineMapper.queryHeadLineList();
        oe.setDataList(headLineList);
        oe.setCount(headLineList.size());
        return oe;
    }


    private void AddHeadLineImg(HeadLine headLine, ImageHolder imageHolder, Integer userId) {
        String dest = PathUtil.getImagePath(userId);
        log.info("HeadLineServiceImpl  dest :{}", dest);
        String relativeAddr = ImageUtil.addImgAddr(dest, imageHolder);
        headLine.setLineImg(contextPath + relativeAddr);
        log.info("存入数据库 ArticleImg", contextPath + relativeAddr);
    }
}
