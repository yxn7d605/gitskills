package com.yx.home.ss.common;

public enum ResponseWrapper {
    SUCCESS("000000", "请求成功"),

    FAILURE("009000", "请求失败"),

    LOGIN_FAIL("001000", "登录失败"),

    USER_PWD_ERROR("001001", "用户名或密码错误"),

    USER_NOT_FOUND("001002", "用户不存在"),

    ;

    private String code;

    private String message;

    ResponseWrapper(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
