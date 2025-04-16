package ru.vsu.amm.java.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class ErrorFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception ex) {
            String requestUri = request instanceof HttpServletRequest
                    ? ((HttpServletRequest) request).getRequestURI()
                    : "N/A";

            logger.error("Ошибка при обработке запроса [URI: {}]: {}",
                    requestUri,
                    ex.getMessage(),
                    ex);

            request.setAttribute("errorMessage", "Произошла ошибка при обработке запроса");
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}