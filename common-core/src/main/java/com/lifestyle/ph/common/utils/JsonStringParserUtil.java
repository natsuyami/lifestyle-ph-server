package com.lifestyle.ph.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifestyle.ph.common.exception.VerificationException;

public class JsonStringParserUtil {
    
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode extractJsonNode(String jsonVal) {
        try {
            return objectMapper.readTree(jsonVal);
        } catch (Exception e) {
            throw new VerificationException("Failed to read string to object");
        }
    }

    public static JsonNode extractJsonNodeList(String jsonVal) {
        try {
            return objectMapper.readTree(jsonVal).get("data");
        } catch (Exception e) {
            throw new VerificationException("Failed to read string to list of object");
        }
    }
}
