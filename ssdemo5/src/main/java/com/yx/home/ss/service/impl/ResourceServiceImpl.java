package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.ResourceMapper;
import com.yx.home.ss.po.Resource;
import com.yx.home.ss.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> queryResourcesByPermissionCodes(List<String> permissionCodes) {
        Example example = new Example(Resource.class);
        example.createCriteria().andIn("permissionCode", permissionCodes);
        List<Resource> resources = resourceMapper.selectByExample(example);

        return resources;
    }
}
