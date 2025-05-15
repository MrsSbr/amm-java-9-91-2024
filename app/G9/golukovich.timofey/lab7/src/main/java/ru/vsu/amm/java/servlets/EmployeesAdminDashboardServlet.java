package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/employees_admin_dashboard")
public class EmployeesAdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            var hotelsManagerService = new EmployeesServiceImpl();
            var employees = hotelsManagerService.getAllEmployees();
            req.setAttribute("employees_table", employees);
            getServletContext().getRequestDispatcher("/employees_admin_dashboard.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        }
    }
}