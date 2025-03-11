package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.service.impl.AuthServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("name");
        String password = req.getParameter("password");

        AuthService authService = new AuthServiceImpl();
        boolean isLoginSuccessful = authService.login(login, password);
    }
}
