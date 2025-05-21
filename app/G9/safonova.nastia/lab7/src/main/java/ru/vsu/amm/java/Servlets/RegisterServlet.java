package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.Employee;
import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String surname = req.getParameter("surname");
        String name = req.getParameter("name");
        String patronumic = req.getParameter("patronumic");
        LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));

        try {
            Employee employee = new Employee(
                    0L,
                    login,
                    password,
                    surname,
                    name,
                    patronumic,
                    birthDate
            );

            AuthService authService = new AuthService();
            authService.register(employee);

            resp.sendRedirect("login");
        } catch (AlreadyExistException | DbException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}