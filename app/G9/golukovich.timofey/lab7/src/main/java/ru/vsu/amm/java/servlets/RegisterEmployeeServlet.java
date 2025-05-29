package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.HotelNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.PostDoesNotExistException;
import ru.vsu.amm.java.services.HotelsService;
import ru.vsu.amm.java.services.RegistrationService;
import ru.vsu.amm.java.services.impl.HotelsServiceImpl;
import ru.vsu.amm.java.services.impl.RegistrationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet("/api/register")
public class RegisterEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HotelsService hotelsService = new HotelsServiceImpl();
            var hotels = hotelsService.getAllHotels(false);

            var employeePosts = Arrays.stream(EmployeePost.values())
                    .filter(p -> p != EmployeePost.MASTER_ADMINISTRATOR)
                    .collect(Collectors.toList());

            req.setAttribute("hotels", hotels);
            req.setAttribute("employee_posts", employeePosts);
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/employees_admin_dashboard").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            Integer hotelId = Integer.parseInt(req.getParameter("hotel_id"));
            String post = req.getParameter("post");
            var employee = (EmployeeDto) req.getSession().getAttribute("employee");

            RegistrationService registrationService = new RegistrationServiceImpl();

            registrationService.registerEmployee(login, password, post, hotelId, employee);
            req.setAttribute("successMessage", "Employee " + login + " was created");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Incorrect hotel id");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (EmployeeAlreadyExistsException | PostDoesNotExistException
                 | HotelNotFoundException | NotAllowedActionException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        }
    }
}
