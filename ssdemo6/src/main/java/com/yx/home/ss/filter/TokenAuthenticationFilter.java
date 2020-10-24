package com.yx.home.ss.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yx.home.ss.bo.JwtAuthenticationToken;
import com.yx.home.ss.service.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器获取jwt token
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    // 处理登录事项
    private UserLoginService userLoginService;

    public void setTokenService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authResult = null;
        AuthenticationException failed = null;
        try {
            String token = userLoginService.parseToken(request);

            if (token == null) {
                // 未登录
                LOGGER.error("LoginTokenAuthenticationFilter->doFilterInternal user have not login");
                failed = new InsufficientAuthenticationException("token is null");
            } else {
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
                authResult = this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
            }
        } catch (JWTVerificationException e) {
            LOGGER.error("LoginTokenAuthenticationFilter->doFilterInternal JWTVerificationException {}", e);
            failed = new InsufficientAuthenticationException("token format error");
        }catch (InternalAuthenticationServiceException e) {
            LOGGER.error("LoginTokenAuthenticationFilter->doFilterInternal InternalAuthenticationServiceException {}", e);
            failed = e;
        } catch (AuthenticationException e) {
            LOGGER.error("LoginTokenAuthenticationFilter->doFilterInternal AuthenticationException {}", e);
            failed = e;
        }

        if(authResult != null) {
            // 处理成功的情况
            successfulAuthentication(request, response, filterChain, authResult);
        } else {
            // 处理失败的情况
            unsuccessfulAuthentication(request, response, failed);

            return;
        }

        filterChain.doFilter(request, response);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);

        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }
}
