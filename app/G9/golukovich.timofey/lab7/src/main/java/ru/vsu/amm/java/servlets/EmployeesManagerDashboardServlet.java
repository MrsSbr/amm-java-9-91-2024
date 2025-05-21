package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/employees_manager_dashboard")
public class EmployeesManagerDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var employee = (EmployeeDto) session.getAttribute("employee");
        try {
            EmployeesService employeesService = new EmployeesServiceImpl();
            var employees = employeesService.getAllFilteredEmployees(
                    null, employee.getHotelId(), null, null, null,
                    null, null, null, "STAFF", null, false);

            req.setAttribute("employees_table", employees);
            getServletContext().getRequestDispatcher("/employees_manager_dashboard.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            session.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
