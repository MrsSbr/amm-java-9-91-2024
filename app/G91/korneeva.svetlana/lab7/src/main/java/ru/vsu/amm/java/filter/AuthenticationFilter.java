package ru.vsu.amm.java.filter;

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
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession httpSession = req.getSession(false);

        String loginUrl = req.getContextPath() + "/login";
        String registerUrl = req.getContextPath() + "/register";
        boolean loggedIn = httpSession != null && httpSession.getAttribute("user") != null;
        boolean loginRequest = req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals(loginUrl + ".jsp");
        boolean registerRequest = req.getRequestURI().equals(registerUrl) || req.getRequestURI().equals("/register.jsp");

        if (loginRequest || registerRequest || loggedIn) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }    }
}
