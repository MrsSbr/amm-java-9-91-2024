package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.Exceptions.UserNotFoundException;
import ru.vsu.amm.java.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String number_phone = req.getParameter("phoneNumber");

        AuthService authService = new AuthService();

        try {
            authService.register(login, password, confirmPassword, email, number_phone);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
