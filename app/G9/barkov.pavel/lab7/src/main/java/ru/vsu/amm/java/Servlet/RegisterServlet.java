package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;
import ru.vsu.amm.java.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("Name");
        String document = req.getParameter("Document");
        String email = req.getParameter("Email");
        String phone = req.getParameter("Phone");
        String password = req.getParameter("Password");
        ShareholderCreateModel user = new ShareholderCreateModel(name, document, email, phone);
        try {
            userService.register(user, password);
            resp.sendRedirect("/login");
        } catch (RuntimeException | SQLException e) {
            //Обработка исключений
        }
    }
}
