package com.yx.home.ss.utils;

import cn.hutool.core.util.HexUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;
import java.util.UUID;

public class StringUtils {
    public static String bCryptPasswordEncode(String str) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return  bCryptPasswordEncoder.encode(str);
    }

    public static String uuid() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
//        System.out.println(StringUtils.bCryptPasswordEncode("9999"));

        System.out.println(HexUtil.encodeHexStr(String.valueOf(StringUtils.uuid().hashCode()).getBytes()));

//        Base64.getEncoder().encodeToString(String.valueOf(StringUtils.uuid().hashCode()).getBytes()
    }
}
