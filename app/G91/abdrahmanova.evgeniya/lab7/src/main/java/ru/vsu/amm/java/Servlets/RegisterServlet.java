package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidDateException;
import ru.vsu.amm.java.Exceptions.InvalidUserCredentialsException;
import ru.vsu.amm.java.Services.AuthenticationService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Arrays.stream(Role.values()).map(Enum::name).toList());
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullname");
        String birthday = req.getParameter("birthdate");
        String phoneNumber = req.getParameter("phone");
        String userRoleName = req.getParameter("user_role");
        try {
            var userRole = Role.valueOf(userRoleName);

            AuthenticationService authService = new AuthenticationService();
            var userDto = authService.register(email, password, fullName, birthday, phoneNumber, userRole);

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", userDto);
            resp.sendRedirect(req.getContextPath() + "/bookings");
        } catch (InvalidUserCredentialsException | InvalidDateException | DatabaseException e) {
            ServletHelper.setError(req, e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            ServletHelper.setError(req, "Ошибка роли");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
