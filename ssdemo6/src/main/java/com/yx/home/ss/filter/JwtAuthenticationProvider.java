package com.yx.home.ss.filter;

import com.yx.home.ss.bo.LoginUserInfo;
import com.yx.home.ss.service.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.NonceExpiredException;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private UserLoginService userLoginService;

    private UserDetailsService userDetailsService;

    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((JwtAuthenticationToken) authentication).getToken();
        if (userLoginService.isLoginExpired(token)) {
            // 判断token是否过期
            throw new NonceExpiredException("token expires");
        } else {
            LoginUserInfo loginUserInfo = userLoginService.parseLoginUserInfo(token);
            loginUserInfo = userLoginService.queryLoginUserInfo(loginUserInfo.getOaCode());
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginUserInfo.getOaCode());
            if (userLoginService.isNeedRefreshToken(token)) {
                // 是否需要刷新token
                token = userLoginService.createToken(loginUserInfo);
            }

            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());

            return jwtAuthenticationToken;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
