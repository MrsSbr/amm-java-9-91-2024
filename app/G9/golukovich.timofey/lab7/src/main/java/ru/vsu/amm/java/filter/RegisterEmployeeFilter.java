package ru.vsu.amm.java.filter;

import ru.vsu.amm.java.enums.EmployeePost;

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
import java.util.logging.Logger;

@WebFilter("/register_employee")
public class RegisterEmployeeFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean isAdmin = (session != null
                && EmployeePost.ADMINISTRATOR.equals(session.getAttribute("post")));
        if (isAdmin) {
            chain.doFilter(request, response);
        } else {
            String login = (session != null) ? (String) session.getAttribute("login") : null;

            logger.info("Unable to access " + login + " to register_employee");
            resp.sendRedirect("/index.jsp");
        }
    }
}
