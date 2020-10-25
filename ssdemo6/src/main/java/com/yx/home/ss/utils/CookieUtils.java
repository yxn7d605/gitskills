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

    public static String getCookieValue(String name, Cookie[] cookies) {
        Cookie cookie = getCookie(name, cookies);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    /**
     * 创建一个临时cookie
     *
     * @param name
     * @param value
     * @return
     */
    public static Cookie buildTmpCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
