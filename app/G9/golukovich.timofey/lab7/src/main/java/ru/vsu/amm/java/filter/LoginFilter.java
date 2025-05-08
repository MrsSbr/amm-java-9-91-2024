package ru.vsu.amm.java.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebFilter("/*")
public class LoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());
    private static final List<String> allowedPaths = List.of(
            "/auth/login",
            "/login.jsp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("employee") != null);
        boolean isPathAllowed = allowedPaths.contains(path);
        if (isLoggedIn || isPathAllowed) {
            chain.doFilter(request, response);
        } else {
            logger.info("Unauthorized access attempt");
            resp.sendRedirect("/auth/login");
        }
    }
}