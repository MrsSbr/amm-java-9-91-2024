package ru.vsu.amm.java.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        boolean loggedIn = httpSession != null && httpSession.getAttribute("email") != null;
        boolean loginRequest = req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals(loginUrl + ".jsp");
        boolean registerRequest = req.getRequestURI().equals(registerUrl) || req.getRequestURI().equals("/register.jsp");

        if (loginRequest || registerRequest || loggedIn) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }    }
}
