package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.service.implementations.DefaultAuthService;
import ru.vsu.amm.java.service.interfaces.AuthService;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private static final String REGISTER_PAGE = "/register.jsp";
    private static final String HOME_PAGE = "/index.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
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

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            RegisterRequest registerRequest = new RegisterRequest(name, email, password);

            authService.register(registerRequest);
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            resp.sendRedirect(HOME_PAGE);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute(ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
        }

    }
}
