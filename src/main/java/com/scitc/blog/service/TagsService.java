package com.scitc.blog.service;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Tags;

public interface TagsService {

    OperationExecution getTagsList() throws BlogException;
    OperationExecution insertTags(Tags tags)throws BlogException;

    OperationExecution getTagsById(Integer tagId)throws BlogException;
    //删除
    OperationExecution deleteTags (Integer tagId)throws BlogException;
    //更新
    OperationExecution updateTags(Tags tags)throws BlogException;
}
