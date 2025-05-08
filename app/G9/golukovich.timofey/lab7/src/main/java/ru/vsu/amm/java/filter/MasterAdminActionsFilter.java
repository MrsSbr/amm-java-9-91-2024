package ru.vsu.amm.java.filter;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.utils.EmployeeDtoChecker;

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

//@WebFilter({"/hotel_master_admin/*", "/employees_dashboard"})
public class MasterAdminActionsFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session != null) {
            var redirectUrl = req.getHeader("Referer");
            if (redirectUrl == null || redirectUrl.isBlank()) {
                redirectUrl = "/main.jsp";
            }

            var employee = (EmployeeDto) session.getAttribute("employee");
            if (EmployeeDtoChecker.isReady(employee)) {
                if (employee.getPost() == EmployeePost.MASTER_ADMINISTRATOR) {
                    chain.doFilter(request, response);
                } else {
                    final String message = "Unable to access the master-administrator's tools";
                    logger.info(employee.getLogin() + ": " + message);
                    session.setAttribute("errorMessage", message);
                    resp.sendRedirect(redirectUrl);
                }
            } else {
                logger.info(employee.getLogin() + ": " + "Unsetted employee data");
                session.setAttribute("errorMessage", "Before starting work, please set your data in the profile");
                resp.sendRedirect(redirectUrl);
            }
        } else {
            logger.info("Unauthorized access attempt");
            resp.sendRedirect("/login");
        }
    }
}
