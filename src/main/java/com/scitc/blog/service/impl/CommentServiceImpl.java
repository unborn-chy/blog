package com.scitc.blog.service.impl;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.CommentMapper;
import com.scitc.blog.model.Comment;
import com.scitc.blog.service.CommentService;
import com.scitc.blog.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public int addComment(Comment comment) throws BlogException {
        int effectedNum = commentMapper.insertComment(comment);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.INNER_ERROR);
        }
        return effectedNum;
    }

    @Override
    @Transactional
    public int deleteComment(Integer commentId) throws BlogException {
        Comment comment = commentMapper.queryCommentById(commentId);
        if(comment==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }
        int effectedNum = commentMapper.deleteComment(commentId);
        if(effectedNum<=0){
            throw new BlogException(OperationEnums.DELETE_SUCCESS);
        }
        return effectedNum;
    }

    @Override
    public Comment getCommentById(Integer commentId) throws BlogException {
        Comment comment = commentMapper.queryCommentById(commentId);
        if(comment==null){
            throw new BlogException(OperationEnums.NULL_INFO);
        }

        return comment;
    }

    @Override
    public OperationExecution getCommentList(Comment comment, int pageIndex, int pageSize) throws BlogException {
        OperationExecution<Comment> op = new OperationExecution<>();
        int rowIndex = CommonUtil.getRowIndex(pageIndex, pageSize);
        List<Comment> commentList = commentMapper.queryComment(comment,rowIndex,pageSize);
        int count  = commentMapper.queryCommentCount(comment);
//        if(commentList.size()==0){
//            throw new BlogException(OperationEnums.NULL_INFO);
//        }
        op.setDataList(commentList);
        op.setCount(count);
        return op;
    }
}
