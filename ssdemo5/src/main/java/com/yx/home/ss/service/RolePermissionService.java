package com.yx.home.ss.service;

import com.yx.home.ss.po.RolePermission;

import java.util.List;

public interface RolePermissionService {
    public List<RolePermission> queryRolePermissionsByRoleCodes(List<String> roleCodes);
}
