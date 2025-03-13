package com.profile.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

public class RequestCachingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest) {
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);

            chain.doFilter(wrappedRequest, response);

            wrappedRequest.getContentAsByteArray(); // 🔥 Garante que o corpo seja armazenado no cache
        } else {
            chain.doFilter(request, response);
        }
    }
}