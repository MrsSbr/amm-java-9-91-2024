package ru.vsu.amm.java.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("user") != null;

        String loginUrl = req.getContextPath() + "/login";
        String registerUrl = req.getContextPath() + "/register";

        boolean isLoginPage = req.getRequestURI().equals(loginUrl)
                || req.getRequestURI().equals(loginUrl + ".jsp");
        boolean isRegisterPage = req.getRequestURI().equals(registerUrl)
                || req.getRequestURI().equals(registerUrl + ".jsp");

        if (isLoginPage || isRegisterPage || loggedIn) {
            logger.log(Level.FINE, "Allowing access to: " + req.getRequestURI());
            chain.doFilter(req, resp);
        } else {
            logger.log(Level.INFO, "Redirecting unauthorized request to " + req.getRequestURI() + " to login page");
            resp.sendRedirect(loginUrl);
        }
    }
}
