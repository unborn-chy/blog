package com.scitc.blog.dto;

import com.scitc.blog.enums.OperationEnums;


import java.util.List;

public class OperationExecution<T> {
    //结果状态
    private Integer state;
    //状态标识
    private String stateInfo;
    //Category数量
    private Integer count;
    //操作 category
    private T data;
    //categoryList
    private List<T> dataList;


    public OperationExecution() {
    }

    public OperationExecution(OperationEnums operationEnums){
        this.state = operationEnums.getState();
        this.stateInfo = operationEnums.getMsg();
    }
    //成功的构造方法
    public OperationExecution(OperationEnums operationEnums, T data){
        this.state = operationEnums.getState();
        this.stateInfo = operationEnums.getMsg();
        this.data = data;
    }
    //成功集合
    public OperationExecution(OperationEnums operationEnums, List<T> dataList){
        this.state = operationEnums.getState();
        this.stateInfo = operationEnums.getMsg();
        this.dataList = dataList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


}
