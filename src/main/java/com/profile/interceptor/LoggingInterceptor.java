package com.profile.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Incoming request to URI: {}", request.getRequestURI());
        MDC.put("flow", "someFlowValue");
        MDC.put("userId", "someUserId");
        MDC.put("requestStatus", "STARTED");
        MDC.put("errorCategory", "none");
        MDC.put("errorType", "none");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.put("requestStatus", "COMPLETED");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Completed request to URI: {}", request.getRequestURI());
        MDC.clear();
    }
}