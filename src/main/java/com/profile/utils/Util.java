package com.profile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.profile.exceptions.ErrorType;
import com.profile.exceptions.ProfileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Util {

    private final ObjectMapper objectMapper;

    public Util() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> String parseObjectToJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("parseObjectToJson - Error in Parse Object To Json! - Error: [{}]", e.getMessage(), e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, e.getMessage());
        }
    }

    public <T> T parseStringToObject(String value, Class<T> type) {
        try {
            return objectMapper.readValue(value, type);
        } catch (Exception ex) {
            log.error("parseStringToObject - Failed to parse object: {}, error: {}", value, ex.getMessage(), ex);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Failed to parse object %s".formatted(value));
        }
    }
}