package ru.vsu.amm.java.filter;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.utils.EmployeePostActionsFilterPaths;

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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebFilter("/api/*")
public class EmployeePostActionsFilter implements Filter {
    private static final Logger logger = Logger.getLogger(EmployeePostActionsFilter.class.getName());
    private static final Map<String, List<EmployeePost>> pathsRequiredAccess = EmployeePostActionsFilterPaths.getRequired();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        HttpSession session = req.getSession(false);
        var employee = (session != null)
                ? (EmployeeDto) session.getAttribute("employee")
                : null;

        if (employee == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        boolean isReady = path.endsWith("/api/main") || path.endsWith("/api/profile") || employee.isReady();
        if (!isReady) {
            logger.info(employee.getLogin() + ": " + "Unsetted employee data");
            session.setAttribute("errorMessage", "Before starting work, please set your data in the profile");
            resp.sendRedirect(req.getContextPath() + "/api/main");
            return;
        }

        boolean isAccessed = checkAccess(path, employee.getPost());
        if (!isAccessed) {
            logger.warning("Access denied for " + employee.getLogin() + " to " + path);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean checkAccess(String path, EmployeePost userRole) {
        for (Map.Entry<String, List<EmployeePost>> entry : pathsRequiredAccess.entrySet()) {
            if (path.equals(entry.getKey())) {
                return entry.getValue().contains(userRole);
            }
        }
        return true;
    }
}
