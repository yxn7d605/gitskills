package com.yx.home.ss.common;

public enum UserStatusEnum {
    IS_EFFECT(1, "有效"),

    NOT_EFFECT(2, "无效");

    private Integer code;

    private String desc;

    private UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean codeEquals(UserStatusEnum userStatusEnum, Integer code) {
        if (code == null) {
            return false;
        }

        if (userStatusEnum.getCode().intValue() == code.intValue()) {
            return true;
        }

        return false;
    }
}
