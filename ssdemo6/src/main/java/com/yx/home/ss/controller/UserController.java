package com.yx.home.ss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/hello")
    public String hello(String name, HttpServletResponse response) {
        System.out.println(name);

        return "hello";
    }

    @GetMapping("/hello1")
    public String hello1(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("token")) {
                cookie.setMaxAge(0);
                cookie.setValue("ffff");

                response.addCookie(cookie);
            }
        }

//        Cookie cookie = new Cookie("token", "wwwwwwwweeeeeeeeeeeeeiiiiiii");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);

        return "hello1";
    }
}
