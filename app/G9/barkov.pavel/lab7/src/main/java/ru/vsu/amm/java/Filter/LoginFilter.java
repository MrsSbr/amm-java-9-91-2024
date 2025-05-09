package ru.vsu.amm.java.Filter;

import ru.vsu.amm.java.Service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("DoFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("userId") != null;
        if (loggedIn) {
            logger.info("Success pass");
            chain.doFilter(req, resp);
        } else {
            String loginUrl = req.getContextPath() + "/login";
            String registerUrl = req.getContextPath() + "/register";
            boolean loginReq = req.getRequestURI().equals(loginUrl) || req.getRequestURI().equals("/login.jsp");
            boolean registerReq = req.getRequestURI().equals(registerUrl) || req.getRequestURI().equals("/register.jsp");
            if (loginReq || registerReq) {
                logger.info("Login or register");
                chain.doFilter(req, resp);
            } else {
                logger.info("Authorization error");
                resp.sendRedirect("/login");
            }
        }
    }
}
