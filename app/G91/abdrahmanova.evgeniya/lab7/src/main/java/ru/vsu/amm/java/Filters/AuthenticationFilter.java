package ru.vsu.amm.java.Filters;

import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        try {
            ServletHelper.getUser(req);
            chain.doFilter(request, response);
        } catch (AuthorizationException e) {
            if (path.endsWith("/login") || path.endsWith("/register")) {
                chain.doFilter(request, response);
            } else {
                logger.info("Unauthorized access");
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }
}
