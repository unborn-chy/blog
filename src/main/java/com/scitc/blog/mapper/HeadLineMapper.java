package com.scitc.blog.mapper;

import com.scitc.blog.model.HeadLine;

import java.util.List;

public interface HeadLineMapper {
    //添加头条
     int insertHeadLine(HeadLine headLine);
    //修改
     int updateHeadLine(HeadLine headLine);
    //删除
     int deleteHeadLine(Integer lineId);
    //根据 lineId查询
    HeadLine queryHeadLineById(Integer lineId);
    //查询全部
    List<HeadLine> queryHeadLineList();
}
