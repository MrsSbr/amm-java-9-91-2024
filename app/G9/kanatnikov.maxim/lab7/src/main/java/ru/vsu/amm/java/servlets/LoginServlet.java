package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.requests.LoginRequest;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private final AuthService authService;

    public LoginServlet() {
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
        String email = req.getParameter("email");
        LoginRequest loginRequest = new LoginRequest(email,
                req.getParameter("password"));
         try {
             UserEntity user = authService.login(loginRequest);
             session.setAttribute("user", user);
             resp.sendRedirect("/catalog");
         } catch (SQLException | AuthenticationException e) {
             logger.log(Level.SEVERE, String.format("Fail to login user %s", email));
             req.setAttribute("errorMessage", e.getMessage());
             getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
         }
    }
}
