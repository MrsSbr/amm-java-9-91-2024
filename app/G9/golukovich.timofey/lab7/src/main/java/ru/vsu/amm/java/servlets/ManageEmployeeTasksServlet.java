package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hotel_manager/employees/manage_tasks")
public class ManageEmployeeTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var hotelsManagerService = new EmployeesServiceImpl();
        var employees = hotelsManagerService.getAllEmployees();
        req.setAttribute("employees_table", employees);
        getServletContext().getRequestDispatcher("/tasks_scheduler.jsp").forward(req, resp);
    }
}
