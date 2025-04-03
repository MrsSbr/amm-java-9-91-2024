package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.RegisterRequest;
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

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private static final String REGISTER_PAGE = "/register.jsp";
    private static final String MAIN_UI = "/index.jsp";
    private final AuthService authService;

    public RegisterServlet() {
        this.authService = new DefaultAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            RegisterRequest registerRequest = new RegisterRequest(name, password);

            authService.register(registerRequest);
            HttpSession session = req.getSession();
            session.setAttribute("login", name);
            resp.sendRedirect(MAIN_UI);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }

    }
}