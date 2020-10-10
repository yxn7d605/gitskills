package com.yx.home.ss.service;

import com.yx.home.ss.po.Resource;

import java.util.List;

public interface ResourceService {
    public List<Resource> queryResourcesByPermissionCodes(List<String> permissionCodes);
}
