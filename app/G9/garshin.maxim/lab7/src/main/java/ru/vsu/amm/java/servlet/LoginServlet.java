package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    AuthService authService;

    @Override
    public void init() throws ServletException {
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            if (authService.login(username, password)) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("username", username);
                resp.sendRedirect("/notes");
            } else {
                req.setAttribute("errorMessage", "Invalid password");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "A user with this login does not exist");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}