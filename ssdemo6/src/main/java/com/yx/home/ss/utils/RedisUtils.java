package com.yx.home.ss.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    private RedisTemplate<String, Object> redisTemplate;
}
