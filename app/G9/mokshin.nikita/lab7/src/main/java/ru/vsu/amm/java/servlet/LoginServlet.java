package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.model.request.CredentialsRequest;
import ru.vsu.amm.java.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private AuthService authService;

    @Override
    public void init() {
        authService = (AuthService) getServletContext().getAttribute("authService");
        logger.info("LoginServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /login");
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        logger.info("Received POST request to /login for user: {}", login);
        try {
            authService.login(new CredentialsRequest(login, password), req);
            logger.info("User '{}' logged in successfully", login);
            resp.sendRedirect("/me");
        } catch (AuthException e) {
            logger.info("Failed login attempt for user '{}': {}", login, e.getMessage());
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
