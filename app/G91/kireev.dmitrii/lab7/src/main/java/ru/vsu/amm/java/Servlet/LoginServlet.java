package ru.vsu.amm.java.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Request.LoginRequest;
import ru.vsu.amm.java.Model.Response.LoginResponse;
import ru.vsu.amm.java.Service.Impl.DefaultAuthServiceImpl;
import ru.vsu.amm.java.Service.Interface.AuthService;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService;

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String ERROR_MESSAGE = "error";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String SESSION_EMAIL = "email";

    private static final String DB_ERROR_MESSAGE = "Ошибка бд";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Неверные данные для входа";

    public LoginServlet() {
        this.authService = new DefaultAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
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
            req.setAttribute(ERROR_MESSAGE, DB_ERROR_MESSAGE);
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(ERROR_MESSAGE, INVALID_CREDENTIALS_MESSAGE);
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}
