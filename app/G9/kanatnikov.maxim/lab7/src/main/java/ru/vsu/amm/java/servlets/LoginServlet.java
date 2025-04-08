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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;

    public LoginServlet() {
        authService = new UserAuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginRequest loginRequest = new LoginRequest(req.getParameter("email"),
                req.getParameter("password"));
         try {
             UserEntity user = authService.login(loginRequest);
             session.setAttribute("user", user);
             resp.sendRedirect("/catalog");
         } catch (SQLException | AuthenticationException e) {
             req.setAttribute("errorMessate", e.getMessage());
             resp.sendRedirect("/login");
         }
    }
}
