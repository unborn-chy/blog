package com.scitc.blog.mapper;

import com.scitc.blog.model.Tags;

import java.util.List;

public interface TagsMapper {
    public int insertTags(Tags tags);
    public Tags queryTagsById(Integer tagId);

    /**
     *全部标签
     * @return
     */
    public List<Tags> queryTags();
    //删除
    int deleteTags (Integer tagId);
    //更新
    int updateTags(Tags tags);
    //count
//    int count(Tags tags);
}
