package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        name = name.isEmpty() ? null : name;

        String phoneNumber = req.getParameter("phone_number");
        phoneNumber = phoneNumber.isEmpty() ? null : phoneNumber;

        String email = req.getParameter("email");
        email = email.isEmpty() ? null : email;

        String passportNumber = req.getParameter("passport_number");
        passportNumber = passportNumber.isEmpty() ? null : passportNumber;

        String passportSeries = req.getParameter("passport_series");
        passportSeries = passportSeries.isEmpty() ? null : passportSeries;

        String birthday = req.getParameter("birthday");
        birthday = birthday.isEmpty() ? null : birthday;

        var employee = (EmployeeDto) req.getSession().getAttribute("employee");

        EmployeesService employeesService = new EmployeesServiceImpl();
        try {
            employee = employeesService.patchEmployee(employee, name, phoneNumber, email, passportNumber, passportSeries, birthday);
            var session = req.getSession();
            session.setAttribute("employee", employee);
            req.setAttribute("successMessage", "Employee + " + employee.getLogin() + " personal data was updated");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (EmployeeAlreadyExistsException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (EmployeeNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/login").forward(req, resp);
        }

        getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
