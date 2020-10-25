package com.yx.home.ss.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.yx.home.ss.service.UserLoginService;
import com.yx.home.ss.utils.CookieUtils;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器获取jwt token
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    private List<RequestMatcher> permissiveRequestMatchers;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authResult = null;
        AuthenticationException failed = null;
        try {
            // 配置不需要token验证的url
            if (isPermissiveRequest(request)) {
                filterChain.doFilter(request, response);

                return;
            }

            String token = CookieUtils.getCookieValue(UserLoginService.TOKEN_NAME, request.getCookies());
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

    private boolean isPermissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null) {
            return false;
        }

        for (RequestMatcher requestMatcher : permissiveRequestMatchers) {
            if (requestMatcher.matches(request)) {
                return true;
            }
        }

        return false;
    }

    public void setPermissiveUris(String... uris) {
        if (permissiveRequestMatchers == null) {
            permissiveRequestMatchers = new ArrayList<>(uris.length);
        }

        for (String uri : uris) {
            permissiveRequestMatchers.add(new AntPathRequestMatcher(uri));
        }
    }
}
