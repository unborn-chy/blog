package com.scitc.blog.mapper;

import com.scitc.blog.model.Advice;
import com.scitc.blog.model.Tags;

import java.util.List;

public interface AdviceMapper {
    int insertAdvice(Advice advice);
    int deleteAdvice(Integer adviceId);
    Advice queryAdviceById(Integer adviceId);
    List<Advice> queryAdvice();

    //更新
    int updateAdvice(Advice advice);
}
