package com.yx.home.ss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yx.home.ss.mapper")
public class Ssdemo5Application {
    public static void main(String[] args) {
        SpringApplication.run(Ssdemo5Application.class, args);
    }
}
