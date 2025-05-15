package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.HotelNotFoundException;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.HotelsService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;
import ru.vsu.amm.java.services.impl.HotelsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var employee = (EmployeeDto) session.getAttribute("employee");
        HotelsService hotelsService = new HotelsServiceImpl();
        try {
            var hotel = hotelsService.getHotelById(employee.getHotelId());
            req.setAttribute("hotel_name", hotel.getName());
            getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (HotelNotFoundException e) {
            session.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        }
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
            getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (EmployeeAlreadyExistsException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (EmployeeNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/auth/login").forward(req, resp);
        }

        getServletContext().getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
