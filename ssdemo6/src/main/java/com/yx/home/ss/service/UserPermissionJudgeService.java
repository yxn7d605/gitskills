package com.yx.home.ss.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface UserPermissionJudgeService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
