package com.yx.home.ss.config;

import com.yx.home.ss.filter.JwtAuthenticationProvider;
import com.yx.home.ss.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/img/**").permitAll()
                .anyRequest().access("@userPermissionJudgeService.hasPermission(request, authentication)")
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().disable();
//                .cors()
//                .and()
//                .apply(new JsonLoginHttpConfigurer<>()).loginAuthenticationSuccessHandler(jsonLoginAuthenticationSuccessHandler())
//                .and()
//                .apply(new TokenAuthenticationConfigurer<>()).permissiveRequestUris("/login")
//                .and()
//                .logout()
//                .and()
//                .sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler jsonLoginAuthenticationSuccessHandler() {
        LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler = new LoginAuthenticationSuccessHandler();
        loginAuthenticationSuccessHandler.setUserLoginService(userLoginService);

        return loginAuthenticationSuccessHandler;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider();
        provider.setUserLoginService(userLoginService);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }
}
