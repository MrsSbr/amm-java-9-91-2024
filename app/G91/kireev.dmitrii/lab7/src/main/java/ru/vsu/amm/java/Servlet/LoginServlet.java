package ru.vsu.amm.java.Servlet;


import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Request.LoginRequest;
import ru.vsu.amm.java.Model.Response.LoginResponse;
import ru.vsu.amm.java.Service.Impl.DefaultAuthServiceImpl;
import ru.vsu.amm.java.Service.Interface.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = "/signin")
public class LoginServlet extends HttpServlet {
    private static final String AUTH_VIEW  = "/signin.jsp";
    private static final String MAIN_UI  = "/home";
    private static final String ERROR_MESSAGE = "error";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String SESSION_EMAIL = "email";

    private static final String DB_ERROR_MESSAGE = "Ошибка бд";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Неверные данные для входа";

    private final AuthService authService;

    public LoginServlet() {
        authService = new DefaultAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAccessInterface(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);

        try {
            LoginResponse loginResponse = authService.login(new LoginRequest(email, password));

            if (loginResponse.code() == 200) {
                HttpSession session = request.getSession();
                session.setAttribute(SESSION_EMAIL, email);
                response.sendRedirect(request.getContextPath()+ MAIN_UI);
            } else {
                request.setAttribute(ERROR_MESSAGE, loginResponse.message());
                getServletContext().getRequestDispatcher(AUTH_VIEW).forward(request, response);
            }
        } catch (DbException e) {
            request.setAttribute(ERROR_MESSAGE, DB_ERROR_MESSAGE);
            getServletContext().getRequestDispatcher(AUTH_VIEW).forward(request, response);
        } catch (Exception e) {
            request.setAttribute(ERROR_MESSAGE, INVALID_CREDENTIALS_MESSAGE);
            getServletContext().getRequestDispatcher(AUTH_VIEW).forward(request, response);
        }
    }

    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(AUTH_VIEW).forward(request, response);
    }
}