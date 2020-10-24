package com.yx.home.ss.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static <T> String obj2Json(T obj, boolean includeNull) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!includeNull) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        return objectMapper.writeValueAsString(obj);
    }

    public static String readValueByKey(String key, String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        String val = jsonNode.get(key).asText();

        return val;
    }
}
