package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidUserCredentialsException;
import ru.vsu.amm.java.Services.AuthenticationService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            AuthenticationService authService = new AuthenticationService();

            var user = authService.login(email, password);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", user);
            resp.sendRedirect( req.getContextPath() + "/bookings");
        } catch (InvalidUserCredentialsException | DatabaseException e) {
            ServletHelper.setError(req, e.getMessage());
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
