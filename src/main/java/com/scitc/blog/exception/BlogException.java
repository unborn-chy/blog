package com.scitc.blog.exception;

import com.scitc.blog.enums.OperationEnums;

public class BlogException extends RuntimeException {
    private Integer code;

    public BlogException(String message){
        super(message);
    }



    public BlogException(OperationEnums operationEnums){
        super(operationEnums.getMsg());
        this.code = operationEnums.getState();
    }

    public BlogException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
