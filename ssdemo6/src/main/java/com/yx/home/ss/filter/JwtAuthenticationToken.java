package com.yx.home.ss.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 封装了当前登录用户的信息
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String token;

    public JwtAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
