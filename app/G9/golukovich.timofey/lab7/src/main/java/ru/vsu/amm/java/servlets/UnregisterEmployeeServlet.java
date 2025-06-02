package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.PostDoesNotExistException;
import ru.vsu.amm.java.services.impl.RegistrationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/unregister")
public class UnregisterEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/unregister_employee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        var employee = (EmployeeDto) req.getSession().getAttribute("employee");

        var hotelsAdminService = new RegistrationServiceImpl();
        try {
            hotelsAdminService.unregisterEmployee(login, employee);
            req.setAttribute("successMessage", "Employee " + login + " was unregistered");
            getServletContext().getRequestDispatcher("/unregister_employee.jsp").forward(req, resp);
        } catch (EmployeeAlreadyExistsException | PostDoesNotExistException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/unregister_employee.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/unregister_employee.jsp").forward(req, resp);
        } catch (NotAllowedActionException e) {
            req.setAttribute("errorMessage", "You has not allowed to unregister employee " + login);
            getServletContext().getRequestDispatcher("/unregister_employee.jsp").forward(req, resp);
        }
    }
}