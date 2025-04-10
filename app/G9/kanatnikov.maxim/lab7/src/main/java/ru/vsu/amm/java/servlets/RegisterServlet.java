package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.service.UserAuthService;
import ru.vsu.amm.java.service.interfaces.AuthService;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        RegisterRequest registerRequest = new RegisterRequest(
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("patronymic"),
                req.getParameter("city"),
                email,
                req.getParameter("phoneNumber"),
                req.getParameter("password")
        );

        try {
            UserEntity user = authService.register(registerRequest);
            session.setAttribute("user", user);
            resp.sendRedirect("/catalog");
        } catch (SQLException | AuthenticationException e) {
            logger.log(Level.SEVERE, String.format("Registration error for user %s", email));
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
