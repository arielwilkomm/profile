package com.profile.exceptions.handler;

import com.profile.exceptions.ExceptionResponse;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Optional.ofNullable;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BAD_REQUEST = "BAD_REQUEST";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        var exceptionResponse = new ExceptionResponse("500", INTERNAL_SERVER_ERROR, ex.getMessage());
        request.getDescription(false);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        var exceptionResponse = new ExceptionResponse("400", BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(ProfileException.class)
    public ResponseEntity<Object> handleProfileException(ProfileException e) {
        var message = e.getMessage();
        ofNullable(e.getErrorType().getLevel()).ifPresent(level -> this.logMessage(level, message, e.getErrorType(), e));

        return ResponseEntity.status(e.getErrorType().getStatus())
                .body(this.buildError(e.getErrorType(), message));
    }

    private ExceptionResponse buildError(ErrorType errorType, String message) {
        return new ExceptionResponse(errorType.getCode(), errorType.name(), message);
    }

    private void logMessage(Level levelLog, String message, ErrorType errorType, ProfileException e) {
        log.atLevel(levelLog).log("errorType: {} message: {}", errorType.getCode(), message, e);
        MDC.clear();
    }
}