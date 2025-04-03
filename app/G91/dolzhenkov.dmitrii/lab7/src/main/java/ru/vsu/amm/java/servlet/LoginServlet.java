package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.service.implementations.DefaultAuthService;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.utils.ErrorMessages;

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
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        try {
            authService.login(new LoginRequest(name, password));

            HttpSession session = req.getSession();
            session.setAttribute("name", name);
            resp.sendRedirect(HOME_PAGE);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}