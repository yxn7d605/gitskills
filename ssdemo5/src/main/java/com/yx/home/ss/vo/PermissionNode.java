package com.yx.home.ss.vo;

import java.util.List;

public class PermissionNode {
    // 权限code
    private String code;

    // 权限名称
    private String name;

    // 该权限的子权限
    private List<PermissionNode> permissions;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionNode> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionNode> permissions) {
        this.permissions = permissions;
    }
}
