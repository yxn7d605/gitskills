package com.yx.home.ss.config;

import com.yx.home.ss.filter.JsonUsernamePasswordAuthenticationFilter;
import com.yx.home.ss.filter.TokenAuthenticationFilter;
import com.yx.home.ss.service.UserLoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class TokenAuthenticationConfigurer<T extends TokenAuthenticationConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private TokenAuthenticationFilter authenticationFilter;

    public TokenAuthenticationConfigurer() {
        this.authenticationFilter = new TokenAuthenticationFilter();
    }

    @Override
    public void configure(B builder) throws Exception {
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());

        TokenAuthenticationFilter filter = postProcess(authenticationFilter);
        builder.addFilterBefore(filter, LogoutFilter.class);
    }

    public TokenAuthenticationConfigurer<T, B> permissiveRequestUris(String... uris) {
        authenticationFilter.setPermissiveUris(uris);

        return this;
    }
}
