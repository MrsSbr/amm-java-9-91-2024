package ru.vsu.amm.java.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Request.LoginRequest;
import ru.vsu.amm.java.Model.Response.LoginResponse;
import ru.vsu.amm.java.Service.Interface.AuthService;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
@AllArgsConstructor
public class LoginServlet extends HttpServlet {

    private final AuthService authService;

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String SESSION_EMAIL = "email";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(EMAIL_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);

        try {
            LoginResponse loginResponse = authService.login(new LoginRequest(email, password));

            if (loginResponse.code() == 200) {
                HttpSession session = req.getSession();
                session.setAttribute(SESSION_EMAIL, email);
                resp.sendRedirect(HOME_PAGE);
            } else {
                req.setAttribute(ERROR_MESSAGE, loginResponse.message());
                getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            }
        } catch (DbException e) {
            req.setAttribute(ERROR_MESSAGE, "Ошибка бд");
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}

