package ru.vsu.amm.java.servlets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/boards/*", "/columns/*", "/tasks/*", "/users/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Пропускаем запросы на регистрацию
        String pathInfo = req.getPathInfo();
        if (req.getServletPath().equals("/users") && (pathInfo == null || pathInfo.equals("/register"))) {
            chain.doFilter(request, response); // Пропускаем без проверки
            return;
        }

        // Проверяем наличие пользователя в сессии
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login"); // Перенаправляем на логин
            return;
        }

        // Пользователь авторизован, передаем запрос дальше
        chain.doFilter(request, response);
    }
}