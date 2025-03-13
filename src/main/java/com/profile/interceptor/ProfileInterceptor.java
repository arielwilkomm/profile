package com.profile.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProfileInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println(">>> Entrou no ProfileInterceptor - Método: " + request.getMethod());

        if (request.getMethod().equalsIgnoreCase("GET")) {
            String cpf = extractCpfFromUrl(request.getRequestURI());
            System.out.println("Extracted CPF from URL: " + cpf);
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }
            request.getInputStream().readAllBytes(); // Força o cache antes da leitura
            String cpf = extractCpfFromBody((ContentCachingRequestWrapper) request);
            System.out.println("Extracted CPF from Body: " + cpf);
        }
        return true;
    }

    private String extractCpfFromUrl(String uri) {
        Pattern pattern = Pattern.compile("/profile/([0-9]+)");
        Matcher matcher = pattern.matcher(uri);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractCpfFromBody(ContentCachingRequestWrapper request) throws IOException {
        byte[] buf = request.getContentAsByteArray();
        if (buf.length == 0) {
            return null;
        }
        String requestBody = new String(buf, StandardCharsets.UTF_8);
        Map<String, Object> body = objectMapper.readValue(requestBody, Map.class);
        return body.get("cpf") != null ? body.get("cpf").toString() : null;
    }
}