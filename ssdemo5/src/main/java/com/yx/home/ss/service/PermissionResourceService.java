package com.yx.home.ss.service;

import java.util.List;
import java.util.Map;

public interface PermissionResourceService {
    public Map<String, List<String>> getPermissionResourceListMap();

    public List<String> queryResourceCodeListByPermissionCode(String permissionCode);

    public List<String> queryResourceCodeListByPermissionCodes(List<String> permissionCodes);
}
