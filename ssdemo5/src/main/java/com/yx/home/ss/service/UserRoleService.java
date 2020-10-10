package com.yx.home.ss.service;

import com.yx.home.ss.po.UserRole;

import java.util.List;

public interface UserRoleService {
    public List<UserRole> queryUserRolesByOaCode(String oaCode);
}
