package com.yx.home.ss.common;

public class ResponseMode {
    private String code;

    private String message;

    private Object data;

    private ResponseMode(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResponseMode(ResponseWrapper responseWrapper) {
        this.code = responseWrapper.getCode();
        this.message = responseWrapper.getMessage();
    }

    private ResponseMode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseMode success() {
        return new ResponseMode(ResponseWrapper.SUCCESS);
    }

    public static ResponseMode fail() {
        return new ResponseMode(ResponseWrapper.FAILURE);
    }

    public static ResponseMode success(Object data) {
        ResponseMode responseMode = success();
        responseMode.data = data;

        return responseMode;
    }

    public static ResponseMode fail(ResponseWrapper responseWrapper) {
        return new ResponseMode(responseWrapper);
    }

    public static ResponseMode fail(String code, String message) {
        return new ResponseMode(code, message);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
