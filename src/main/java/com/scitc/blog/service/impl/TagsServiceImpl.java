package com.scitc.blog.service.impl;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.TagsMapper;
import com.scitc.blog.model.Tags;
import com.scitc.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
//    @Cacheable("tagList")
    public OperationExecution getTagsList() {
        List<Tags> tagsList = tagsMapper.queryTags();
        OperationExecution<Tags> oe = new OperationExecution<>();
        oe.setDataList(tagsList);
        oe.setCount(tagsList.size());
        return oe;
    }

    @Override
    @Transactional
    public OperationExecution insertTags(Tags tags) throws BlogException {
        tags.setCreateTime(new Date());
        int effectedNum = tagsMapper.insertTags(tags);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.INNER_ERROR);
        }
        return new OperationExecution(OperationEnums.INNER_SUCCESS);
    }

    @Override
    public OperationExecution getTagsById(Integer tagId) throws BlogException {
        Tags tags = tagsMapper.queryTagsById(tagId);
        if(tags==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        return new OperationExecution<Tags>(OperationEnums.SUCCESS, tags);

    }

    @Override
    @Transactional
    public OperationExecution deleteTags(Integer tagId) throws BlogException {
        try {
            Tags tags = tagsMapper.queryTagsById(tagId);
            int effected = tagsMapper.deleteTags(tags.getTagId());
            if (effected <= 0) {
                throw  new BlogException(OperationEnums.DELETE_ERROR);
            }
            return new OperationExecution(OperationEnums.DELETE_SUCCESS);
        } catch (Exception e) {
            throw new BlogException(OperationEnums.NULL_INFO);
        }
    }

    @Override
    @Transactional
    public OperationExecution updateTags(Tags tags) throws BlogException {
        int effectedNum = tagsMapper.updateTags(tags);
        if (effectedNum <= 0) {
            throw  new BlogException(OperationEnums.UPDATE_ERROR);
        }
        return new OperationExecution(OperationEnums.UPDATE_SUCCESS);
    }

}
