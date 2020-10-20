package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.PermissionResourceMapper;
import com.yx.home.ss.po.PermissionResource;
import com.yx.home.ss.service.PermissionResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionResourceServiceImpl implements PermissionResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionResourceMapper permissionResourceMapper;

    private Map<String, List<String>> permissionResourceListMap = null;

    @PostConstruct
    private void buildPermissionResourceListMap() {
        List<PermissionResource> permissionResources = permissionResourceMapper.selectAll();
        if (permissionResources != null && !permissionResources.isEmpty()) {
            permissionResourceListMap = new HashMap<>();
            for (PermissionResource permissionResource : permissionResources) {
                if (permissionResourceListMap.get(permissionResource.getPermissionCode()) == null) {
                    List<String> resourceCodes = new ArrayList<>();
                    resourceCodes.add(permissionResource.getResourceCode());
                    permissionResourceListMap.put(permissionResource.getPermissionCode(), resourceCodes);
                } else {
                    permissionResourceListMap.get(permissionResource.getPermissionCode()).add(permissionResource.getResourceCode());
                }
            }
        }
    }

    @Override
    public Map<String, List<String>> getPermissionResourceListMap() {
        return permissionResourceListMap;
    }

    @Override
    public List<String> queryResourceCodeListByPermissionCode(String permissionCode) {
        if (permissionResourceListMap != null) {
            return permissionResourceListMap.get(permissionCode);
        }

        return null;
    }

    @Override
    public List<String> queryResourceCodeListByPermissionCodes(List<String> permissionCodes) {
        List<String> resouceCodes = new ArrayList<>();
        for (String permissionCode : permissionCodes) {
            List<String> lst = queryResourceCodeListByPermissionCode(permissionCode);
            if (lst != null && !lst.isEmpty()) {
                resouceCodes.addAll(lst);
            }
        }

        return resouceCodes;
    }
}
