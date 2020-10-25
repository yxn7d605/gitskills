package com.yx.home.ss.service;

import com.yx.home.ss.bo.LoginUserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserLoginService {
    public static final String TOKEN_NAME = "access_token";

    /**
     * 判断用户是否是登录用户，目前的判断是是否携带了token
     *
     * @param request
     * @return
     */
    public boolean isLoggedIn(HttpServletRequest request);

    /**
     * 目前从cookie中获取token
     *
     * @param request
     * @return
     */
    public String parseToken(HttpServletRequest request);

    /**
     * 根据token判断是否过期登录
     *
     * @param token
     * @return
     */
    public boolean isLoginExpired(String token);

    /**
     * 判断是否需要刷新token
     *
     * @param token
     * @return
     */
    public boolean isNeedRefreshToken(String token);

    /**
     * 生成一个新的token
     *
     * @param loginUserInfo
     * @return
     */
    public String createToken(LoginUserInfo loginUserInfo);

    /**
     * 获取登录用户最新信息，建议从缓存中获取
     *
     * @param oaCode
     * @return
     */
    public LoginUserInfo queryLoginUserInfo(String oaCode);

    /**
     * 从token中解析登录用户信息
     *
     * @param token
     * @return
     */
    public LoginUserInfo parseLoginUserInfo(String token);

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token);
}
