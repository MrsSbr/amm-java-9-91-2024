package ru.vsu.amm.java.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpMethodFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger((HttpMethodFilter.class));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.debug("Filtering request: {}",httpRequest.getRequestURI());

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String method = httpRequest.getParameter("_method");
            if (method != null) {
                log.debug("Overriding POST method to: {}", method.toUpperCase());
                HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
                    @Override
                    public String getMethod() {
                        return method.toUpperCase();
                    }
                };
                chain.doFilter(wrappedRequest, response);
                return;
            }
        }
        log.debug("Proceeding with original method");
        chain.doFilter(request, response);
    }
}