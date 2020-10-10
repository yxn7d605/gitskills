package com.yx.home.ss.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static <T> String obj2Json(T obj, boolean includeNull) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!includeNull) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return objectMapper.writeValueAsString(obj);
    }
}
