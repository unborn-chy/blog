package com.scitc.blog.service.impl;


import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.AdviceMapper;
import com.scitc.blog.model.Advice;
import com.scitc.blog.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdviceServiceImpl implements AdviceService {

    @Autowired
   private AdviceMapper adviceMapper;

    @Override
    @Transactional
    public int addAdvice(Advice advice) throws BlogException {
        advice.setCreateTime(new Date());
        int effectedNum = adviceMapper.insertAdvice(advice);
        if(effectedNum<=0){
            throw new BlogException(OperationEnums.INNER_ERROR);
        }
        return effectedNum;
    }

    @Override
    @Transactional
    public int updateAdvice(Advice advice) throws BlogException {
        int effectedNum = adviceMapper.updateAdvice(advice);
        if(effectedNum<=0){
            throw new BlogException(OperationEnums.UPDATE_ERROR);
        }
        return effectedNum;
    }

    @Override
    @Transactional
    public int deleteAdvice(Integer adviceId) throws BlogException {
        int effectedNum = adviceMapper.deleteAdvice(adviceId);
        if(effectedNum<=0){
            throw new BlogException(OperationEnums.DELETE_ERROR);
        }
        return effectedNum;
    }

    @Override
    public Advice getAdviceById(Integer adviceId) throws BlogException {
       Advice advice = adviceMapper.queryAdviceById(adviceId);
       return advice;
    }

    @Override
    public List<Advice> getAdviceList() throws BlogException {
        return adviceMapper.queryAdvice();
    }
}
