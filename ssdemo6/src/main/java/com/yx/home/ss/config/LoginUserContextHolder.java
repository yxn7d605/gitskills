package com.yx.home.ss.config;

import com.yx.home.ss.bo.LoginUserInfo;

public class LoginUserContextHolder {
    private static final ThreadLocal<LoginUserInfo> threadLocalContext = new ThreadLocal();;

    public static LoginUserInfo getLoginUserInfo() {
        return threadLocalContext.get();
    }

    public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        threadLocalContext.set(loginUserInfo);
    }
}
