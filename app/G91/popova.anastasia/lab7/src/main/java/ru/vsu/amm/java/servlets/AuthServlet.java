package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger((AuthServlet.class));

    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing GET request to /auth{}", req.getPathInfo());

        String action = req.getPathInfo();
        if (action == null || action.equals("/")) {
            log.debug("Redirecting to login page");
            resp.sendRedirect("/auth/login");
            return;
        }
        switch (action) {
            case "/login":
                req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
                break;
            case "/register":
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
                break;
            default:
                log.warn("Invalid path for auth: {}", action);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing POST request to /auth{}", req.getPathInfo());

        String action = req.getPathInfo();
        switch (action) {
            case "/login":
                handleLogin(req, resp);
                break;
            case "/register":
                handleRegister(req, resp);
                break;
            case "/logout":
                handleLogout(req, resp);
                break;
            default:
                log.warn("Invalid path for auth: {}", action);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        log.debug("Attempting to register user with email: {}", email);

        if (name == null || email == null || password == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            log.warn("Missing registration fields");
            req.setAttribute("error", "Все поля обязательны для заполнения!");
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            return;
        }
        try {
            User user = userService.register(name, email, password);
            req.getSession().setAttribute("user", user);
            log.info("Successfully registered user: {}", user.getUserID());
            resp.sendRedirect("/boards");
        } catch (RuntimeException e) {
            log.error("Failed to register user with email: {}", email, e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        log.debug("Attempting login for email: {}", email);

        try {
            User user = userService.login(email, password);
            req.getSession().setAttribute("user", user);
            log.info("Successfully logged in user: {}", user.getUserID());
            resp.sendRedirect("/boards");
        } catch (Exception e) {
            log.error("Failed to login user with email: {}", email, e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.debug("Processing logout request");
        req.getSession().invalidate();
        log.info("User logged out");
        resp.sendRedirect("/auth/login");
    }

}
