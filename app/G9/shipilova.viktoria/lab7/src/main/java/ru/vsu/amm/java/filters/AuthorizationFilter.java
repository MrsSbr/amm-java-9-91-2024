package ru.vsu.amm.java.filters;

import ru.vsu.amm.java.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String[] publicPages = {
                "index.jsp",
                "lost_property_office/",
                "login.jsp",
                "register.jsp",
                "login",
                "register",
                "authorization",
                "authorization.jsp",
                "listProperties"
        };
        boolean isPublicPage = Arrays.stream(publicPages).anyMatch(requestURI::endsWith);

        HttpSession session = httpRequest.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (!isPublicPage && user == null) {
            String redirectTo = httpRequest.getRequestURI();
            if (httpRequest.getQueryString() != null) {
                redirectTo += "?" + httpRequest.getQueryString();
            }

            httpRequest.getSession().setAttribute("redirectTo", redirectTo);
            httpResponse.sendRedirect("authorization");
            return;
        }

        chain.doFilter(request, response);
    }
}