package com.yx.home.ss.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @PostMapping("hello")
    public String hello(String tt) throws IOException {
        System.out.println(tt);

        byte[] bts = Base64.decodeBase64(tt);
        File file = new File("C:\\Users\\yxn7d\\Desktop\\doc1.docx");
        FileCopyUtils.copy(bts, file);

        return "hello, admin";
    }
}
