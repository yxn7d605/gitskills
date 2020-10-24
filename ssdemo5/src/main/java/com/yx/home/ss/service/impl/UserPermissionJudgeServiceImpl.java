package com.yx.home.ss.service.impl;

import com.yx.home.ss.po.Resource;
import com.yx.home.ss.service.PermissionResourceService;
import com.yx.home.ss.service.ResourceService;
import com.yx.home.ss.service.RolePermissionService;
import com.yx.home.ss.service.UserPermissionJudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userPermissionJudgeService")
public class UserPermissionJudgeServiceImpl implements UserPermissionJudgeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPermissionJudgeServiceImpl.class);

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionResourceService permissionResourceService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {
            List<Resource> resources = queryResourceUris((UserDetails) principal);
            if (resources != null && !resources.isEmpty()) {
                hasPermission = matchResourceUri(resources, request.getRequestURI(), request.getMethod());
            }
        }

        return true;
    }

    private boolean matchResourceUri(List<Resource> resources, String requestUri, String method) {
        LOGGER.info("UserPermissionJudgeServiceImpl->matchResourceUri request uri: [{}], request method: [{}]", requestUri, method);

        for (Resource resource : resources) {
            if (antPathMatcher.match(resource.getUri(), requestUri) && method.equalsIgnoreCase(resource.getReqMethod())) {
                LOGGER.info("UserPermissionJudgeServiceImpl->matchResourceUri match uri: [{}], method: [{}]", resource.getUri(), resource.getReqMethod());

                return true;
            }
        }

        return false;
    }

    /**
     * 查询资源列表
     *
     * @param userDetails
     * @return
     */
    private List<Resource> queryResourceUris(UserDetails userDetails) {
        // 获取用户角色列表
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        if (grantedAuthorities == null || grantedAuthorities.isEmpty()) {
            return null;
        }

        // 查询权限列表
        List<String> roleCodes = new ArrayList<>(grantedAuthorities.size());

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleCodes.add(grantedAuthority.getAuthority());
        }
        List<String> permissionCodes = rolePermissionService.queryPermissionsByRoleCodes(roleCodes);

        // 查询资源列表
        List<String> resourceCodes = permissionResourceService.queryResourceCodeListByPermissionCodes(permissionCodes);

        List<Resource> resources = resourceService.queryResourceListByCodes(resourceCodes);

        return resources;
    }
}
