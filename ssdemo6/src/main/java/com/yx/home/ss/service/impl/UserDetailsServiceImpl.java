package com.yx.home.ss.service.impl;

import com.yx.home.ss.bo.LoginUserInfo;
import com.yx.home.ss.config.LoginUserContextHolder;
import com.yx.home.ss.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserInfo loginUserInfo = LoginUserContextHolder.getLoginUserInfo();
        // TODO 这里需要查询本地数据库获取用户信息
        User user = new User();
        if (user == null) {
            throw new UsernameNotFoundException("user was not found");
        }

        // TODO 这里需要判断用户的状态是否有效

        // TODO 这里需要查询角色信息

        if (!isLoginSuccess(loginUserInfo)) {
            LOGGER.error("UserDetailsServiceImpl->loadUserByUsername user login fail, username: [{}]", username);

            throw new BadCredentialsException("user login fail");
        }

        return org.springframework.security.core.userdetails.User.builder().username("Jack").password(passwordEncoder.encode("jack-password")).roles("USER").build();
    }

    private User queryLocalUser(String username) {
        return null;
    }

    private boolean isLoginSuccess(LoginUserInfo loginUserInfo) {
        // TODO 调用接口对登录用户进行验证

        return true;
    }
}
