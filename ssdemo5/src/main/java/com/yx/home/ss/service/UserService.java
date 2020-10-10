package com.yx.home.ss.service;

import com.yx.home.ss.po.User;

public interface UserService {
    public User loadUserByOaCode(String oaCode);
}
