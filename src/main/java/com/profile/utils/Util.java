package com.profile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.profile.exceptions.ErrorType;
import com.profile.exceptions.ProfileException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
@UtilityClass
public class Util {

    public static <T> String parseObjectToJson(T object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("parseObjectToJson - Error in Parse Object To Json ! - Error: [{}]", e.getMessage(), e);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, e.getMessage());
        }
    }

    public static Object parseStringToObject(String value, Class<?> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(value, type);
        } catch (Exception ex) {
            log.error("parseStringToObject - Failed to parse object: {}, error: {}", value, ex.getMessage(), ex);
            throw new ProfileException(ErrorType.INTERNAL_ERROR, "Failed to parse object %s".formatted(value));
        }
    }
}
