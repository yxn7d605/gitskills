package com.yx.home.ss.service;

public interface RedisService {
    public Object get(String key);

    public void set(String key, Object value);

    public void set(String key, Object value, long timeout);

    /**
     * 递增
     *
     * @param key
     * @param n
     * @return
     */
    public long incr(String key, long n);
}
