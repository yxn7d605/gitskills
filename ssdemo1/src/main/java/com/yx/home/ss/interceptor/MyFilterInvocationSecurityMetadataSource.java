package com.yx.home.ss.interceptor;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Map<String, String[]> PERMMAP;
    static {
        PERMMAP = new HashMap<>();
        PERMMAP.put("/user/api/hello", new String[] {"ROLE_ADMIN", "ROLE_USER"});
        PERMMAP.put("/admin/api/hello", new String[] {"ROLE_ADMIN"});
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) o;
        String requestUrl = filterInvocation.getRequestUrl();
        for (String s : PERMMAP.keySet()) {
            if (antPathMatcher.match(s, requestUrl)) {
                return SecurityConfig.createList(PERMMAP.get(s));
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
