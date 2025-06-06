package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.service.implementations.DefaultAuthService;
import ru.vsu.amm.java.service.interfaces.AuthService;

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
    private static final String HOME_PAGE = "/index.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private final AuthService authService;

    public RegisterServlet() {
        this.authService = new DefaultAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            RegisterRequest registerRequest = new RegisterRequest(name, email, password, Role.USER);

            authService.register(registerRequest);
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            response.sendRedirect(HOME_PAGE);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }

    }
}
