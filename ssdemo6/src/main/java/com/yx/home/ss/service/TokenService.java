package com.yx.home.ss.service;

import com.yx.home.ss.bo.LoginUserInfo;

public interface TokenService {
    public String createToken(LoginUserInfo loginUserInfo);

    public LoginUserInfo verifyToken(String token);

    public LoginUserInfo parseLoginUserInfo(String token);
}
