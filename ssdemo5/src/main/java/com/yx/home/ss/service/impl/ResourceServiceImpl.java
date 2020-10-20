package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.ResourceMapper;
import com.yx.home.ss.po.Resource;
import com.yx.home.ss.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

    private List<Resource> resources = null;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 系统启动是加载所有资源
     */
    @PostConstruct
    private void loadResouces() {
        LOGGER.info("ResourceServiceImpl->loadResouces begin load resources...");

        resources = resourceMapper.selectAll();

        LOGGER.info("ResourceServiceImpl->loadResouces end load resources, resource size: [{}]", resources == null ? null : resources.size());
    }

    @Override
    public List<Resource> getAllResources() {
        return resources;
    }

    @Override
    public List<Resource> queryResourceListByCodes(List<String> codes) {
        if (resources != null && !resources.isEmpty()) {
            List<Resource> resources = new ArrayList<>();
            for (String code : codes) {
                for (Resource resource : resources) {
                    if (code.equals(resource.getCode())) {
                        resources.add(resource);
                    }
                }
            }

            return resources;
        }

        return null;
    }
}
