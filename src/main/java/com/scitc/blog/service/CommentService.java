package com.scitc.blog.service;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Comment;

import java.util.List;

public interface CommentService {
    //添加
    int addComment(Comment comment) throws BlogException;

    //删除
    int deleteComment(Integer commentId) throws BlogException;

    //id查询
    Comment getCommentById(Integer commentId) throws BlogException;

    //集合
    OperationExecution getCommentList(Comment comment, int pageIndex, int pageSize) throws BlogException;
}
