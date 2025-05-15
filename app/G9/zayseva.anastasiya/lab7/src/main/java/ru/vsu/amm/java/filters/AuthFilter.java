package ru.vsu.amm.java.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> ALLOWED_PATHS = Arrays.asList(
            "/auth/login",
            "/auth/logout",
            "/static/",
            "/register",
            "/css/",
            "/js/"
    );


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean isAllowedPath = ALLOWED_PATHS.stream().anyMatch(path::startsWith);

        if (isAllowedPath) {
            chain.doFilter(request, response);
            return;
        }

        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loggedInUser == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            return;
        }

        chain.doFilter(request, response);
    }
}