package com.profile.exceptions;

import lombok.Getter;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import static com.profile.constants.AppConstants.BUSINESS;
import static com.profile.constants.AppConstants.INTERNAL;

@Getter
public enum ErrorType {
    //business
    PROFILE_NOT_FOUND(BUSINESS, "GB001", HttpStatus.NOT_FOUND, Level.ERROR),
    CEP_NOT_FOUND(BUSINESS, "GB002", HttpStatus.NOT_FOUND, Level.ERROR),
    ADDRESS_NOT_FOUND(BUSINESS, "GB003", HttpStatus.NOT_FOUND, Level.ERROR),

    //internal
    INTERNAL_ERROR(INTERNAL, "GI001", HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR),
    JPA_EXCEPTION(INTERNAL, "GI002", HttpStatus.BAD_GATEWAY, Level.ERROR),
    CONVERT_TO_OBJECT_ERROR(INTERNAL, "GI003", HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR);

    private final String category;
    private final String code;
    private final HttpStatus status;
    private final Level level;

    ErrorType(String category, String code, HttpStatus status, Level level) {
        this.category = category;
        this.code = code;
        this.status = status;
        this.level = level;
    }
}
