package com.scitc.blog.config;

import com.scitc.blog.dto.Result;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.utils.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof BlogException){
            BlogException blogException = (BlogException)e;
            return ResultUtils.error(blogException.getCode(),blogException.getMessage());
        }else {
            return ResultUtils.error(-1,e.toString());
        }
    }
}
