package com.yx.home.ss.config;

import com.yx.home.ss.common.ResponseMode;
import com.yx.home.ss.service.UserLoginService;
import com.yx.home.ss.utils.CookieUtils;
import com.yx.home.ss.utils.JsonUtils;
import com.yx.home.ss.utils.JwtUitls;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功返回成功消息
 */
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserLoginService userLoginService;

    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
//        String token = userLoginService.createToken();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JsonUtils.obj2Json(ResponseMode.success(), false));
        printWriter.flush();
        printWriter.close();
    }
}
