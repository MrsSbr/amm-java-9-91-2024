package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.exception.WrongUserCredentialsException;
import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.service.impl.AuthServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/reg")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        AuthService authService = new AuthServiceImpl();

        try {
            authService.register(login, password);

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            resp.sendRedirect("/currencies");
        } catch (WrongUserCredentialsException | DatabaseException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }
}
