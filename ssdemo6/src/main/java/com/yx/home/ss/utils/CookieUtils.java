package com.yx.home.ss.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie setCookie(String name, String value, int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);

        return cookie;
    }

    public static Cookie getCookie(String name, Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }
}
