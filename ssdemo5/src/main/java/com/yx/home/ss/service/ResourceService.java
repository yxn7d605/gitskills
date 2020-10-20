package com.yx.home.ss.service;

import com.yx.home.ss.po.Resource;

import java.util.List;

public interface ResourceService {
    public List<Resource> getAllResources();

    /**
     * 根据资源code列表查询资源列表
     *
     * @param codes
     * @return
     */
    public List<Resource> queryResourceListByCodes(List<String> codes);
}
