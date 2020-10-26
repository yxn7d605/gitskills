package com.yx.home.ss.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    public Object get(String key);

    public void set(String key, Object value);

    public void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 递增
     *
     * @param key
     * @param n
     * @return
     */
    public long incr(String key, long n);
}
