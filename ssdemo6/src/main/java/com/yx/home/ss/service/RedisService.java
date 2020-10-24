package com.yx.home.ss.service;

public interface RedisService {
    public Object get(String key);

    public void set(String key, Object value);

    /**
     * 递增
     *
     * @param key
     * @param n
     * @return
     */
    public long incr(String key, long n);
}
