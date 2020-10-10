package com.yx.home.ss.exception;

public class GammaRuntimeException extends RuntimeException {
    private String code;

    private String msg;

    public GammaRuntimeException(String code, String msg) {
        super(code + " : " + msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
