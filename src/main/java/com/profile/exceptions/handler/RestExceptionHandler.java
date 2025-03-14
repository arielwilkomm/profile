package com.profile.exceptions.handler;

import com.profile.exceptions.ExceptionResponse;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"GB001\",\n  \"message\": \"PROFILE_NOT_FOUND\",\n  \"details\": [\"Profile not found\"]\n}")
                    )),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class),
                            examples = @ExampleObject(value = "{\n  \"code\": \"GI001\",\n  \"message\": \"INTERNAL_SERVER_ERROR\",\n  \"details\": [\"Unexpected error occurred\"]\n}")
                    ))
    })
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        var exceptionResponse = new ExceptionResponse("500", INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
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