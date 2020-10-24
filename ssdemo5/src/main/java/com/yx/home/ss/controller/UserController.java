package com.yx.home.ss.controller;

import com.yx.home.ss.form.UserQueryForm;
import com.yx.home.ss.po.User;
import com.yx.home.ss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(String name) {
        User user = userService.loadUserByOaCode("0001");
        if (user != null) {
            System.out.println("user: " + user.getOaCode());
        } else {
            System.out.println("0001-user is null");
        }

        return "hello";
    }

    @GetMapping("/query")
    public String query(@Validated UserQueryForm userQueryForm) {
        User user = userService.loadUserByOaCode("0001");
        if (user != null) {
            System.out.println("user: " + user.getOaCode());
        } else {
            System.out.println("0001-user is null");
        }

        return "hello";
    }
}
