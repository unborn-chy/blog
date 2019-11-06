package com.scitc.blog.service;


import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Advice;

import java.util.List;

public interface AdviceService {
    int addAdvice(Advice advice) throws BlogException;
    int updateAdvice(Advice advice) throws BlogException;
    int deleteAdvice(Integer adviceId) throws BlogException;
    Advice getAdviceById(Integer adviceId)throws BlogException;
    List<Advice> getAdviceList()throws BlogException;

}
