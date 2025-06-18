package ru.vsu.amm.java.servlets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@WebFilter(urlPatterns = {"/boards/*", "/columns/*", "/tasks/*", "/users/*"})
public class AuthFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger((ColumnServlet.class));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Пропускаем запросы на регистрацию
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        log.debug("Filtering request to {}{}", servletPath, pathInfo != null ? pathInfo : "");
        if (req.getServletPath().equals("/users") && (pathInfo == null || pathInfo.equals("/register"))) {
            chain.doFilter(request, response); // Пропускаем без проверки
            return;
        }

        // Проверяем наличие пользователя в сессии
        if (req.getSession().getAttribute("user") == null) {
            log.warn("Unauthorized access attempt to {}{}", servletPath, pathInfo != null ? pathInfo : "");
            resp.sendRedirect(req.getContextPath() + "/auth/login"); // Перенаправляем на логин
            return;
        }

        // Пользователь авторизован, передаем запрос дальше
        log.debug("Authorized request proceeding to {}{}", servletPath, pathInfo != null ? pathInfo : "");
        chain.doFilter(request, response);
    }
}