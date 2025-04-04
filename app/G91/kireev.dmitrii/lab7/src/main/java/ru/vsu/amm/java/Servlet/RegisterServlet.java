package ru.vsu.amm.java.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Request.RegisterRequest;
import ru.vsu.amm.java.Model.Response.RegisterResponse;
import ru.vsu.amm.java.Service.Interface.AuthService;
import lombok.AllArgsConstructor;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
@AllArgsConstructor
public class RegisterServlet extends HttpServlet {

    private final AuthService authService; // Сервис для регистрации

    private static final String REGISTER_PAGE = "/register.jsp"; // Страница регистрации
    private static final String HOME_PAGE = "/index.jsp"; // Страница после успешной регистрации
    private static final String ERROR_MESSAGE = "errorMessage"; // Атрибут для хранения ошибки

    private static final String NAME_PARAM = "name"; // Параметр для имени
    private static final String EMAIL_PARAM = "email"; // Параметр для email
    private static final String PASSWORD_PARAM = "password"; // Параметр для пароля
    private static final String PHONE_PARAM = "phone"; // Параметр для телефона

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Перенаправляем на страницу регистрации
        getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter(NAME_PARAM);
        String email = req.getParameter(EMAIL_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);
        String phone = req.getParameter(PHONE_PARAM);

        try {
            RegisterRequest registerRequest = new RegisterRequest(name, email, password, phone);

            RegisterResponse registerResponse = authService.register(registerRequest);

            if (registerResponse.code() == 200) {
                HttpSession session = req.getSession();
                session.setAttribute("email", email);
                resp.sendRedirect(HOME_PAGE);
            } else {
                req.setAttribute(ERROR_MESSAGE, registerResponse.message());
                getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
            }
        } catch (DbException e) {
            req.setAttribute(ERROR_MESSAGE, "Ошибка бд");
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }
    }
}
