package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.UserRoleMapper;
import com.yx.home.ss.po.UserRole;
import com.yx.home.ss.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> queryUserRolesByOaCode(String oaCode) {
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("oaCode", oaCode);

        List<UserRole> userRoles = userRoleMapper.selectByExample(example);

        return userRoles;
    }
}
