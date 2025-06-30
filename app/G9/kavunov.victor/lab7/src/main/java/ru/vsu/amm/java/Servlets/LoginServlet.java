package ru.vsu.amm.java.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Requests.LoginRequest;
import ru.vsu.amm.java.Services.Interfaces.AuthService;
import ru.vsu.amm.java.Services.UserAuthService;
import ru.vsu.amm.java.Exceptions.AuthenticationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private AuthService authService;

    @Override
    public void init() {
        authService = new UserAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        LoginRequest loginRequest = new LoginRequest(login,
                req.getParameter("password"));
        try {
            User user = authService.login(loginRequest);
            session.setAttribute("user", user);
            session.setAttribute("loginUsed", login);
            resp.sendRedirect("/catalog");
        } catch (SQLException | AuthenticationException e) {
            logger.log(Level.SEVERE, String.format("Fail to login user %s", login));
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
