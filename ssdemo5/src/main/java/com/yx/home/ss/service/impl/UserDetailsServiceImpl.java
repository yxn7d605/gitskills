package com.yx.home.ss.service.impl;

import com.yx.home.ss.po.Role;
import com.yx.home.ss.po.User;
import com.yx.home.ss.po.UserRole;
import com.yx.home.ss.service.UserRoleService;
import com.yx.home.ss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 通过OA号获取当前登录用户信息
     *
     * @param s OA号
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.loadUserByOaCode(s);
        if (user == null) {
            throw new UsernameNotFoundException("user was not found");
        }

        // 设置角色
        List<UserRole> userRoles = userRoleService.queryUserRolesByOaCode(user.getOaCode());
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getRoleCode());
                grantedAuthorities.add(grantedAuthority);
            }
        }

        // 需要转换为security框架的user
        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), grantedAuthorities);
    }
}
