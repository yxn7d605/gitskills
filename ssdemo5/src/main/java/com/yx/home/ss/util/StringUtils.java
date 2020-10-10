package com.yx.home.ss.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringUtils {
    public static String bCryptPasswordEncode(String str) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return  bCryptPasswordEncoder.encode(str);
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.bCryptPasswordEncode("9999"));
    }
}
