package com.lifestyle.ph.common.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOBuilder<T> {

    private static DTOBuilder<?> instance;

    // Private constructor to prevent instantiation from outside
    private DTOBuilder() {
    }

    // Public method to get the singleton instance
    public static <T> DTOBuilder<T> getInstance() {
        if (null == instance) {
            instance = new DTOBuilder<>();
        }

        return (DTOBuilder<T>) instance;
    }


    public String objectToJsonString(T obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
