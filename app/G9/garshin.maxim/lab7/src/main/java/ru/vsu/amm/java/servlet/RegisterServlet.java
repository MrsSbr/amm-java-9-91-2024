package ru.vsu.amm.java.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.exception.RegisterException;
import ru.vsu.amm.java.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class.getName());
    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request to /register");
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String confirmPassword = req.getParameter("confirmPassword");

        logger.info("Attempting to register user: {}, email: {}", username, email);

        if (!password.equals(confirmPassword)) {
            logger.warn("Passwords do not match for user: {}", username);
            req.setAttribute("errorMessage", "Passwords don't match");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        try {
            if (authService.register(username, password, email)) {
                logger.info("User registered successfully: {}", username);
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("username", username);
                resp.sendRedirect("/notes");
            } else {
                logger.warn("Registration failed for user: {}", username);
            }
        } catch (DatabaseException | RegisterException e) {
            logger.error("Registration error for user {}: {}", username, e.getMessage());
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}