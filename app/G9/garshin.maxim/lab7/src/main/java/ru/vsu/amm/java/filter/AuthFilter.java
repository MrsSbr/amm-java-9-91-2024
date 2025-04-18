package ru.vsu.amm.java.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.UserEntity;

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

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private final List<String> allowedPaths = List.of(
            "/login",
            "/login.jsp",
            "/register",
            "/register.jsp",
            "/home",
            "/home.jsp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpSession session = req.getSession();

        boolean isLogged = session != null && session.getAttribute("username") != null;
        boolean isAllowedPath = allowedPaths.contains(path);

        logger.info("Request URI: {}", path);
        logger.info("User logged in: {}", isLogged);

        if (isLogged || isAllowedPath) {
            String username;

            if (session != null) {
                username = (String) session.getAttribute("username");
                if (username != null) {
                    logger.info("Authenticated user id: {}", username);
                }
            }

            chain.doFilter(request, response);
        } else {
            logger.warn("Unauthorized access attempt to: {}", path);
            resp.sendRedirect("/login");
        }
    }
}