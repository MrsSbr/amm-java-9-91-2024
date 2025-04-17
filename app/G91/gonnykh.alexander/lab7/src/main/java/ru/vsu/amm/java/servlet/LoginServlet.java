package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.service.implementations.UserService;
import ru.vsu.amm.java.service.implementations.DefaultAuthService;
import ru.vsu.amm.java.service.interfaces.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private final AuthService authService;

    public LoginServlet() {
        this.authService = new DefaultAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            authService.login(new LoginRequest(email, password));


            HttpSession session = req.getSession();
            session.setAttribute("email", email);

            resp.sendRedirect("/availableCars");
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}