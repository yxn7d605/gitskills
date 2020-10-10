package com.yx.home.ss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/loginPage")
    public String login() {
        return "login_page";
    }
}
