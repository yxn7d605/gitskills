package com.yx.home.ss.service;

import com.yx.home.ss.po.Permission;
import com.yx.home.ss.vo.PermissionNode;

import java.util.List;

public interface PermissionService {
    public List<Permission> getAllPermissions();

    /**
     * 查询权限树
     *
     * @return
     */
    public List<PermissionNode> queryPermissionTree();
}
