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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final AuthService authService;

    public RegisterServlet() {
        authService = new UserAuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RegisterRequest registerRequest = new RegisterRequest(
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("patronymic"),
                req.getParameter("city"),
                req.getParameter("email"),
                req.getParameter("phoneNumber"),
                req.getParameter("password")
        );

        try {
            UserEntity user = authService.register(registerRequest);
            session.setAttribute("user", user);
            resp.sendRedirect("/catalog");
        } catch (SQLException | AuthenticationException e) {
            req.setAttribute("errorMessate", e.getMessage());
            resp.sendRedirect("/register");
        }
    }
}
