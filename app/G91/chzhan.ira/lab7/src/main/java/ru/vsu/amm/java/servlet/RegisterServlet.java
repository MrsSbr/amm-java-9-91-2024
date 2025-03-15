package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exception.AlreadyExistsException;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.service.AuthenticationService;

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
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        AuthenticationService authService = new AuthenticationService();

        try {
            authService.register(name, password);

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", name);
            resp.sendRedirect("/index");
        } catch (AlreadyExistsException | DbException e) {
            System.out.println(e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
