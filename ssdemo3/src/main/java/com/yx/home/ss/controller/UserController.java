package com.yx.home.ss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/api")
public class UserController {
    @GetMapping("hello")
    public String hello() {
        return "hello, user";
    }

    @GetMapping("ta")
    public void ta(HttpServletResponse response) {
        response.setStatus(401);
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("www-authenticate", "Basic Realm=\"test\"");
    }
}
