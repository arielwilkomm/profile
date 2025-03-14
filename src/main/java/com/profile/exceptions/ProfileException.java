package com.profile.exceptions;

import lombok.Getter;

@Getter
public class ProfileException extends RuntimeException {
    private final ErrorType errorType;

    public ProfileException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ProfileException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}
