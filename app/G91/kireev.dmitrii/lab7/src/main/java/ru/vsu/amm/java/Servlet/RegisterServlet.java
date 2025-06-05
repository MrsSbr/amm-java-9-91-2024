package ru.vsu.amm.java.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Request.RegisterRequest;
import ru.vsu.amm.java.Model.Response.RegisterResponse;
import ru.vsu.amm.java.Service.Impl.DefaultAuthServiceImpl;
import ru.vsu.amm.java.Service.Interface.AuthService;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private final AuthService authService;

    private static final String REGISTER_PAGE = "/register.jsp";
    private static final String HOME_PAGE = "/home.jsp";
    private static final String ERROR_MESSAGE = "error";

    private static final String NAME_PARAM = "name";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String PHONE_PARAM = "phone";

    private static final String DB_ERROR_MESSAGE = "Ошибка бд";

    public RegisterServlet() {
        this.authService = new DefaultAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showAccessInterface(req, resp);
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
            req.setAttribute(ERROR_MESSAGE, DB_ERROR_MESSAGE);
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }
    }
    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
    }
}
