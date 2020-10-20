package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.RolePermissionMapper;
import com.yx.home.ss.po.RolePermission;
import com.yx.home.ss.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> queryRolePermissionsByRoleCodes(List<String> roleCodes) {
        Example example = new Example(RolePermission.class);
        example.createCriteria().andIn("roleCode", roleCodes);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);

        return rolePermissions;
    }

    @Override
    public List<String> queryPermissionsByRoleCodes(List<String> roleCodes) {
        List<RolePermission> rolePermissions = queryRolePermissionsByRoleCodes(roleCodes);
        List<String> permissions = new ArrayList<>(rolePermissions.size());
        if (rolePermissions != null && !rolePermissions.isEmpty()) {
            for (RolePermission rolePermission : rolePermissions) {
                permissions.add(rolePermission.getPermissionCode());
            }
        }

        return permissions;
    }
}
