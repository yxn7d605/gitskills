package com.yx.home.ss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
    @GetMapping("")
    public void index1(HttpServletResponse response) {
        //内部重定向
        try {
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "index，首页";
    }

    @RequestMapping("/loginError")
    @ResponseBody
    public String loginError() {
        return "loginError，失败页面";
    }
}
