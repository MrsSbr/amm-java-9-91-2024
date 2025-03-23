package ru.vsu.amm.java.servlet;


import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.AuthenticationService;
import ru.vsu.amm.java.service.impl.AuthenticationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
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
            authService.register(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            resp.sendRedirect("/index.jsp");
        } catch (AuthException | DatabaseException e) {

        }
    }
}
