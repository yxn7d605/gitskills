package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.PermissionMapper;
import com.yx.home.ss.po.Permission;
import com.yx.home.ss.service.PermissionService;
import com.yx.home.ss.vo.PermissionNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionMapper permissionMapper;

    private List<Permission> permissions = null;

    @PostConstruct
    private void loadPermissions() {
        LOGGER.info("PermissionServiceImpl->loadPermissions begin load resources...");

        permissions = permissionMapper.selectAll();

        LOGGER.info("PermissionServiceImpl->loadPermissions end load resources, resource size: [{}]", permissions == null ? null : permissions.size());
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissions;
    }

    @Override
    public List<PermissionNode> queryPermissionTree() {
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }

        // 查询根节点列表
        List<PermissionNode> roots = getPermissionNodesByParentCode(null);
        for (PermissionNode permissionNode : roots) {
            List<PermissionNode> nodes = buildPermissionNodes(permissionNode);
            permissionNode.setPermissions(nodes);
        }

        return roots;
    }

    private List<PermissionNode> buildPermissionNodes(PermissionNode permissionNode) {
        List<PermissionNode> nodes = getPermissionNodesByParentCode(permissionNode.getCode());
        if (nodes != null && !nodes.isEmpty()) {
            permissionNode.setPermissions(nodes);

            for (PermissionNode node : nodes) {
                buildPermissionNodes(node);
            }
        }

        return nodes;
    }

    public static void main(String[] args) {
        PermissionServiceImpl permissionService = new PermissionServiceImpl();
        permissionService.permissions = new ArrayList<>();

        Permission permission1 = new Permission();
        permission1.setCode("10001");
        permission1.setName("name1");

        Permission permission2 = new Permission();
        permission2.setCode("10002");
        permission2.setName("name2");

        Permission permission3 = new Permission();
        permission3.setCode("10003");
        permission3.setName("name3");
        permission3.setParentCode("10001");

        Permission permission4 = new Permission();
        permission4.setCode("10004");
        permission4.setName("name4");
        permission4.setParentCode("10003");

        Permission permission5 = new Permission();
        permission5.setCode("10005");
        permission5.setName("name5");

        Permission permission6 = new Permission();
        permission6.setCode("10006");
        permission6.setName("name6");
        permission6.setParentCode("10002");

        Permission permission7 = new Permission();
        permission7.setCode("10007");
        permission7.setName("name7");
        permission7.setParentCode("10001");

        permissionService.permissions.add(permission1);
        permissionService.permissions.add(permission2);
        permissionService.permissions.add(permission3);
        permissionService.permissions.add(permission4);
        permissionService.permissions.add(permission5);
        permissionService.permissions.add(permission6);
        permissionService.permissions.add(permission7);

        List<PermissionNode> roots = permissionService.getPermissionNodesByParentCode(null);

        for (PermissionNode pn : roots) {
            List<PermissionNode> nodes = permissionService.buildPermissionNodes(pn);
            System.out.println(nodes == null ? null : nodes.size());
        }

        System.out.println(roots.size());
    }

    private List<PermissionNode> getPermissionNodesByParentCode(String parentCode) {
        List<PermissionNode> nodes = null;
        for (Permission permission : permissions) {
            if ((parentCode == null && permission.getParentCode() == null) || (parentCode != null && parentCode.equals(permission.getParentCode()))) {
                PermissionNode node = new PermissionNode();
                node.setCode(permission.getCode());
                node.setName(permission.getName());
                if (nodes == null) {
                    nodes = new ArrayList<>();
                }
                nodes.add(node);
            }
        }

        return nodes;
    }
}
