package com.scitc.blog.utils;

import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;

public class ResultUtils {

    //自改
    //成功 带消息,带数据
    public static Result success(OperationEnums operationEnums,Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(operationEnums.getMsg());
        result.setData(object);
        return result;
    }
    //成功 带消息,带数据
    public static Result success(OperationEnums operationEnums){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(operationEnums.getMsg());
        result.setData(null);
        return result;
    }

    public static Result success(String msg){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    //失败 枚举 状态码 消息
    public static Result error(OperationEnums operationEnums){
        Result result = new Result();
        result.setCode(operationEnums.getState());
        result.setMsg(operationEnums.getMsg());
        result.setData(null);
        return result;
    }

    //    //失败 带状态码 消息
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(1500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

//
//    //成功带消息
//    public static Result success(String msg,Object object){
//        Result result = new Result();
//        result.setCode(0);
//        result.setMsg(msg);
//        result.setData(object);
//        return result;
//    }
//
//    //空成功
//    public static Result success(){
//        return success(null);
//    }
//
//    //失败 带状态码 消息
//    public static Result error(Integer code,String msg){
//        Result result = new Result();
//        result.setCode(code);
//        result.setMsg(msg);
//        result.setData(null);
//        return result;
//    }
//
//    //失败 带消息
//    public static Result error(String msg){
//        Result result = new Result();
//        result.setCode(1001);
//        result.setMsg(msg);
//        result.setData(null);
//        return result;
//    }
}
