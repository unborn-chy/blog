package com.scitc.blog.service;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.HeadLine;

public interface HeadLineService {
    OperationExecution addHeadLine(HeadLine headLine, ImageHolder imageHolder,Integer userId) throws BlogException;
    OperationExecution updateHeadline(HeadLine headLine,ImageHolder imageHolder,Integer userId)throws BlogException;
    OperationExecution deleteHeadLine(Integer lineId)throws BlogException;
    OperationExecution getHeadLine(Integer lineId)throws BlogException;
    OperationExecution getHeadLineList() throws BlogException;
}
