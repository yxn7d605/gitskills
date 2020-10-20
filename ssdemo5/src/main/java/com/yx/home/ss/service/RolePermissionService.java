package com.yx.home.ss.service;

import com.yx.home.ss.po.RolePermission;

import java.util.List;

public interface RolePermissionService {
    /**
     * 通过角色列表查询权限列表
     *
     * @param roleCodes
     * @return
     */
    public List<RolePermission> queryRolePermissionsByRoleCodes(List<String> roleCodes);

    /**
     * 返回权限code列表
     *
     * @param roleCodes
     * @return
     */
    public List<String> queryPermissionsByRoleCodes(List<String> roleCodes);
}
