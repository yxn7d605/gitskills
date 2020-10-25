package com.yx.home.ss.filter;

import com.yx.home.ss.bo.LoginUserInfo;
import com.yx.home.ss.config.LoginUserContextHolder;
import com.yx.home.ss.utils.JsonUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 支持json登录方式，必须POST方式提交
 */
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String JSON_USERNAME_KEY = "username";

    public static final String JSON_PASSWORD_KEY = "password";

    public JsonUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

        String userName = null;
        String userPassword = null;
        if (body != null && !body.isEmpty()) {
            userName = JsonUtils.readValueByKey(JSON_USERNAME_KEY, body);
            userPassword = JsonUtils.readValueByKey(JSON_PASSWORD_KEY, body);
        }

        if (userName == null) {
            userName = "";
        }

        if (userPassword == null) {
            userPassword = "";
        }

        userName = userName.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, userPassword);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        // 构建登录信息
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode(userName);
        loginUserInfo.setPassword(userPassword);
        LoginUserContextHolder.setLoginUserInfo(loginUserInfo);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
