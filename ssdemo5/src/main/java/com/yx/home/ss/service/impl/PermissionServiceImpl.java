package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.PermissionMapper;
import com.yx.home.ss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;


}
