package ru.vsu.amm.java.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.logging.Logger;

public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class.getName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("call doFilter in Custom filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession httpSession = req.getSession(false);

        String loginUrl = req.getContextPath() + "/login";
        String registerUrl = req.getContextPath() + "/reg";
        boolean loggedIn = httpSession != null && httpSession.getAttribute("user") != null;
        boolean loginRequest = req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals(loginUrl + ".jsp");
        boolean registerRequest = req.getRequestURI().equals(registerUrl) || req.getRequestURI().equals("/registration.jsp");

        if (loginRequest || registerRequest || loggedIn) {
            log.info("Custom filter successfully passed");
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}