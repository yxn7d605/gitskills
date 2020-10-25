package com.yx.home.ss.service.impl;

import com.yx.home.ss.service.UserPermissionJudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("userPermissionJudgeService")
public class UserPermissionJudgeServiceImpl implements UserPermissionJudgeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPermissionJudgeServiceImpl.class);

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
        }

        return true;
    }
}
