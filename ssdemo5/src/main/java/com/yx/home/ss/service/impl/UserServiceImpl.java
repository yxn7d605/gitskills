package com.yx.home.ss.service.impl;

import com.yx.home.ss.mapper.UserMapper;
import com.yx.home.ss.po.User;
import com.yx.home.ss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserByOaCode(String oaCode) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("oaCode", oaCode);

        User user = userMapper.selectOneByExample(example);

        return user;
    }
}
