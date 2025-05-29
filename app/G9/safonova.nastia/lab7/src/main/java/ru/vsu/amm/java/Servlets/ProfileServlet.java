package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.Employee;
import ru.vsu.amm.java.Services.EmployeeService;
import ru.vsu.amm.java.Exception.DbException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("user");

        if (login == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            Employee employee = employeeService.getEmployeeByLogin(login);
            req.setAttribute("employee", employee);
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (DbException e) {
            resp.sendRedirect("error.jsp");
        }
    }
}