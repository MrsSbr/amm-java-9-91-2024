package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Requests.RegisterRequest;
import ru.vsu.amm.java.Services.Interfaces.AuthService;
import ru.vsu.amm.java.Services.UserAuthService;

import ru.vsu.amm.java.Exceptions.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    private final AuthService authService;

    public RegisterServlet() {
        authService = new UserAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        try {

            RegisterRequest registerRequest = new RegisterRequest(
                    req.getParameter("surname"),
                    req.getParameter("name"),
                    req.getParameter("patronymicname"),
                    req.getParameter("phoneNumber"),
                    email,
                    req.getParameter("password")
            );

            User user = authService.register(registerRequest);
            session.setAttribute("user", user);
            resp.sendRedirect("/catalog");
        } catch (SQLException | AuthenticationException e) {
            logger.log(Level.SEVERE, String.format("Registration error for user %s", email));
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
