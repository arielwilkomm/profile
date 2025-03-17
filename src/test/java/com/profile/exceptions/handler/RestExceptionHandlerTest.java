package com.profile.exceptions.handler;

import com.profile.exceptions.ExceptionResponse;
import com.profile.exceptions.ProfileException;
import com.profile.exceptions.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestExceptionHandlerTest {

    private RestExceptionHandler exceptionHandler;
    private WebRequest request;

    @BeforeEach
    void setUp() {
        exceptionHandler = new RestExceptionHandler();
        request = mock(WebRequest.class);
    }

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ExceptionResponse body = (ExceptionResponse) response.getBody();
        assertNotNull(body);
        assertEquals("500", body.getCode());
        assertEquals("INTERNAL_SERVER_ERROR", body.getMessage());
        assertEquals("Unexpected error", body.getDetails().get(0));
    }

    @Test
    void handleProfileException_ShouldReturnProperResponse() {
        ErrorType errorType = ErrorType.PROFILE_NOT_FOUND;
        ProfileException exception = new ProfileException(errorType, "Profile not found");

        ResponseEntity<Object> response = exceptionHandler.handleProfileException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ExceptionResponse body = (ExceptionResponse) response.getBody();
        assertNotNull(body);
        assertEquals(errorType.getCode(), body.getCode());
        assertEquals(errorType.name(), body.getMessage());
        assertEquals("Profile not found", body.getDetails().get(0));

        assertEquals("ERROR", MDC.get("requestStatus"));
        assertEquals(errorType.name(), MDC.get("errorType"));
        assertEquals("PROCESSING_ERROR", MDC.get("errorCategory"));
    }
}