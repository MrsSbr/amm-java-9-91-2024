package ru.vsu.amm.java.servlet;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@AllArgsConstructor
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        AuthenticationService service = new AuthenticationService(DatabaseAccess.getDataSource());
        if (service.login(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect("/laba7/home");
        } else {
            req.setAttribute("savedLogin", login);
            req.setAttribute("errorMessage", "Неверный логин или пароль");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
