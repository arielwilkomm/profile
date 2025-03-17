package com.profile.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileInterceptorTest {

    private ProfileInterceptor interceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HandlerMethod handlerMethod;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interceptor = new ProfileInterceptor();
    }

    @Test
    void testPreHandle_Success() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/profile/12345678900");
        when(handlerMethod.getMethod()).thenReturn(ProfileInterceptorTest.class.getDeclaredMethod("dummyMethod"));

        boolean result = interceptor.preHandle(request, response, handlerMethod);

        assertTrue(result);
        assertEquals("12345678900", MDC.get("userId"));
        assertEquals("IN_PROGRESS", MDC.get("requestStatus"));
    }

    @Test
    void testPreHandle_Exception() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/profile/invalidCpf");
        when(handlerMethod.getMethod()).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> interceptor.preHandle(request, response, handlerMethod));
        assertEquals("ERROR", MDC.get("requestStatus"));
    }

    void dummyMethod() {}
}