package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Services.AuthService;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");

        AuthService authService = new AuthService();
        try {
            authService.login(login, password);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            resp.sendRedirect("/index.jsp");
        } catch (NotFoundException | DatabaseException e) {
            System.out.println(e.getMessage());
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}