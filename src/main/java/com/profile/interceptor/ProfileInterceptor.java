package com.profile.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProfileInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ProfileInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            if (handler instanceof HandlerMethod) {
                String methodName = ((HandlerMethod) handler).getMethod().getName();
                MDC.put("flow", getMethodName(methodName));
            }

            MDC.put("requestMethod", request.getMethod());
            MDC.put("requestStatus", "IN_PROGRESS");

            String cpf = null;
            if (StringUtils.equalsAnyIgnoreCase(request.getMethod(), "GET", "DELETE", "PUT")) {
                cpf = extractCpfFromUrl(request.getRequestURI());
            }

            if (cpf != null) {
                MDC.put("userId", cpf);
            }
            log.info("ProfileInterceptor - Processing request");

            return true;
        } catch (Exception e) {
            MDC.put("requestStatus", "ERROR");
            MDC.put("errorCategory", "PROCESSING_ERROR");
            MDC.put("errorType", e.getClass().getSimpleName());
            log.error("ProfileInterceptor - Error processing request: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            MDC.put("requestStatus", "ERROR");
        } else if (!StringUtils.equals(MDC.get("requestStatus"), "ERROR")) {
            MDC.put("requestStatus", "SUCCESS");
        }
        log.info("ProfileInterceptor - Processing completed.");
        MDC.clear();
    }

    private String getMethodName(String methodName) {
        switch (methodName) {
            case "getProfile":
                return "GET_PROFILE";
            case "createProfile":
                return "CREATE_PROFILE";
            default:
                return methodName;
        }
    }

    private String extractCpfFromUrl(String uri) {
        Pattern pattern = Pattern.compile("/profile/([0-9]+)");
        Matcher matcher = pattern.matcher(uri);
        return matcher.find() ? matcher.group(1) : null;
    }
}