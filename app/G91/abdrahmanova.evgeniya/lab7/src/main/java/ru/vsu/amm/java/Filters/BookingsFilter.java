package ru.vsu.amm.java.Filters;

import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/bookings")
public class BookingsFilter implements Filter {
    private static final Logger logger = Logger.getLogger(BookingsFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        var user = ServletHelper.getUser(req);
        if (user.userRole() == Role.USER) {
            chain.doFilter(request, response);
        } else {
            logger.info("Unauthorized access");
            resp.sendRedirect(req.getContextPath() + "/tours");
        }
    }
}
