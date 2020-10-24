package com.yx.home.ss.config;

import com.yx.home.ss.filter.JsonUsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

public class JsonLoginHttpConfigurer<T extends JsonLoginHttpConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private JsonUsernamePasswordAuthenticationFilter authenticationFilter;

    public JsonLoginHttpConfigurer() {
        this.authenticationFilter = new JsonUsernamePasswordAuthenticationFilter();
    }

    @Override
    public void configure(B builder) throws Exception {
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());
        authenticationFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        JsonUsernamePasswordAuthenticationFilter filter = postProcess(authenticationFilter);
        builder.addFilterAfter(filter, LogoutFilter.class);
    }
}
