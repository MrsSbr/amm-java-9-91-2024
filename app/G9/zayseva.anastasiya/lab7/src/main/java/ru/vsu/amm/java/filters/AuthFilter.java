package ru.vsu.amm.java.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private final List<String> allowedPaths = List.of(
            "/",
            "",
            "/home",
            "/auth/login",
            "/login.jsp",
            "/auth/register",
            "/register.jsp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean isAllowedPath = allowedPaths.contains(path);

        logger.info("Request received: method={}, path={}, loggedIn={}", req.getMethod(), path, loggedIn);

        if (loggedIn || isAllowedPath) {
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("user");
            }
            logger.info("Access granted: path={} (loggedIn={}, allowed={}) user={}", path, loggedIn, isAllowedPath, user);
            filterChain.doFilter(request, response);
        } else {
            logger.info("Unauthorized access attempt: path={}", path);
            resp.sendRedirect("/auth/login");
        }
    }
}