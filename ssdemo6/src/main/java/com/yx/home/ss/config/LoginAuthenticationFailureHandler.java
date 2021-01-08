package com.yx.home.ss.config;

import com.yx.home.ss.common.ResponseMode;
import com.yx.home.ss.common.ResponseWrapper;
import com.yx.home.ss.utils.JsonUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录失败返回错误提示消息
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseMode responseMode = null;

        if (exception instanceof BadCredentialsException) {
            responseMode = ResponseMode.fail(ResponseWrapper.USER_PWD_ERROR);
        } else if (exception instanceof UsernameNotFoundException) {
            responseMode = ResponseMode.fail(ResponseWrapper.USER_NOT_FOUND);
        } else if (exception instanceof InsufficientAuthenticationException) {
            responseMode = ResponseMode.fail(ResponseWrapper.FAILURE);
        } else if (exception instanceof DisabledException) {
            responseMode = ResponseMode.fail(ResponseWrapper.FAILURE);
        } else {
            responseMode = ResponseMode.fail(ResponseWrapper.LOGIN_FAIL);
        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JsonUtils.obj2Json(responseMode, false));
        printWriter.flush();
        printWriter.close();
    }
}
