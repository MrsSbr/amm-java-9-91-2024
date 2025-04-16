package ru.vsu.amm.java.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/protected/*")
public class AuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Отказ в доступе: пользователь не аутентифицирован [URI: {}, IP: {}]",
                    httpReq.getRequestURI(),
                    httpReq.getRemoteAddr());
            httpResp.sendRedirect(httpReq.getContextPath() + "/login");
            return;
        }

        logger.debug("Доступ разрешен [Пользователь: {}, URI: {}, Роль: {}]",
                session.getAttribute("user"),
                httpReq.getRequestURI(),
                session.getAttribute("role"));
        chain.doFilter(request, response);
    }
}