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

@WebFilter("/hotel_admin/*")
public class AdministratorActionsFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session != null) {
            var employee = (EmployeeDto) session.getAttribute("employee");
            if (EmployeeDtoChecker.isReady(employee)) {
                if (employee.getPost().compareTo(EmployeePost.ADMINISTRATOR) >= 0) {
                    chain.doFilter(request, response);
                } else {
                    logger.info("Unable to access " + employee.getLogin() + " to administrator instruments");
                    resp.sendRedirect("/hotels_management");
                }
            } else {
                logger.info("Unset data of employee " + employee.getLogin());
                resp.sendRedirect("/hotels_management");
            }
        } else {
            logger.info("Unauthorized access attempt");
            resp.sendRedirect("/login");
        }
    }
}
