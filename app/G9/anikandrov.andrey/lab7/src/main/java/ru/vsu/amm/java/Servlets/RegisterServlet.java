package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, "Register page requested");
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        logger.log(Level.INFO, "Registration try for user: " + login);

        String password = req.getParameter("password");

        AuthService authService = new AuthService();

        try {
            authService.register(login, password);

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);

            logger.log(Level.INFO, "Successfully registered user: " + login);

            resp.sendRedirect("/login");
        } catch (AlreadyExistException | DatabaseException e) {
            req.setAttribute("errorMessage", e.getMessage());

            logger.log(Level.WARNING, "Registration failed:" + e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}