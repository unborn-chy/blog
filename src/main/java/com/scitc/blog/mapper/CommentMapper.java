package com.scitc.blog.mapper;

import com.scitc.blog.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int insertComment(Comment comment);
    int updateComment(Comment comment);
    int deleteComment(Integer commentId);
    Comment queryCommentById(Integer commentId);
    List<Comment> queryComment(@Param("comment") Comment comment,@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    int queryCommentCount(@Param("comment") Comment comment);
}
