package com.scitc.blog.enums;

public enum OperationEnums {
    SUCCESS(0, "操作成功"),
    OPERATION_ERROR(1001,"操作失败"),
    NULL_ID(1002, "Id为空"),
    NULL_INFO(1003, "信息为空"),
    INNER_ERROR(1004, "添加失败"),
    UPDATE_ERROR(1005, "更新失败"),
    DELETE_ERROR(1006, "删除失败"),

    INNER_SUCCESS(1007, "添加成功"),
    UPDATE_SUCCESS(1008, "更新成功"),
    DELETE_SUCCESS(1009, "删除成功"),


    ADDIMAGE_ERROR(1010,"图片添加失败"),
    UPDATE_IMAGE_ERROR(1011,"更新图片地址失败"),
    NULL_IMAGE(1012,"图片为空"),
    EMAIL_EXIST(1013,"邮箱已被注册"),
    REGISTER_ERROR(1014,"注册失败"),
    USERNAME__EXIST(1015,"用户名已被注册"),
    USER_EMAIL__EXIST(1016,"用户名或者邮箱已存在"),
    NEWPSW_EQUAL_OLDPSW(1017,"新旧密码不能相同"),
    NAME_PSW_NULL(1018,"用户名密码均不能为空或者空格"),
    PSW_ERROR(1019,"用户密码错误"),
    PSW_IS_NULL(1020,"密码不能为空或者空字符串"),
    PSW_UPDATE_ERROR(1021,"密码更新失败"),
    LOGIN_SUCCESS(1022,"登录成功"),
    LOGIN_ERROR(1023,"用户或者密码错误"),
    NO_LOGIN(1024,"未登录"),
    UPDATE_SUCCESS_RE_LOGIN(1025,"修改成功请重新登录"),
    REGISTER_SUCCESS(1026,"注册成功，开始登录吧"),
    USERNAME_EMAIL_ERROR(1027,"用户名或者邮箱出错"),
    EMAIL_SEND_SUCCESS(1028,"邮件发送成功，请注意查收"),
    EMAIL_SEND_ERROR(1029,"邮件发送失败"),

    ARTICLE_LIST_ERROR(1030,"文章列表获取失败"),
    THUMBNAIL_ERROR(1031, "缩略图跟新失败"),
    ARTICLE_USER_NOT_YOU(1032, "该文章不属于你"),
    TAG_NOT_NULL(1033, "标签不能为空"),
    INNER_SUCCESS_CHECK(1034, "添加成功请等待审核"),
    INPUT_COMPLETE_INFO(1035, "请输入文章信息"),

    NO_AUTHORITY(1036, "没有操作权限"),
    LIKE_COUNT_SUCCESS(1037, "点赞成功"),
    REPEAT_LIKE_COUNT(1038, "请勿重复点赞"),
    REPLAY_SUCCESS(1039, "评论成功"),
    ONLY_AUTHOR_REPLAY(1040, "只有作者能回复"),
    UPDATE_AUTHORITY(1041,"更新用户权限失败"),



    ON_ERROR(1008, "未知错误");

    private Integer state;
    private String msg;

    OperationEnums(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
