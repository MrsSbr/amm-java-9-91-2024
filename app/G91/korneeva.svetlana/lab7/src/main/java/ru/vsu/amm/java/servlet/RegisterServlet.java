package ru.vsu.amm.java.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.AuthenticationService;
import ru.vsu.amm.java.service.impl.AuthenticationServiceImpl;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        AuthenticationService authService = new AuthenticationServiceImpl();
        try {
            UserEntity user = authService.register(email, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect("/transactions");
        } catch (AuthException | DatabaseException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }
}
