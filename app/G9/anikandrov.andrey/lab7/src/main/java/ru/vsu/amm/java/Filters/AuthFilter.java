package ru.vsu.amm.java.Filters;

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
import java.util.Arrays;
import java.util.List;


@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("user") != null;

        String loginUrl = req.getContextPath() + "/login";
        String registerUrl = req.getContextPath() + "/reg";

        boolean isLoginPage = req.getRequestURI().equals(loginUrl)
                || req.getRequestURI().equals(loginUrl + ".jsp");
        boolean isRegisterPage = req.getRequestURI().equals(registerUrl);

        if (isLoginPage || isRegisterPage || loggedIn) {
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect(loginUrl);
        }
    }
}
