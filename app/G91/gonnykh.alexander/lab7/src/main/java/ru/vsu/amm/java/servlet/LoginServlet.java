package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
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
    private static final String ERROR_MESSAGE = "errorMessage";
    private final AuthService authService;

    public LoginServlet() {
        this.authService = new DefaultAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserDto newUser = authService.login(new LoginRequest(email, password));

            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            switch (newUser.getRole()) {
                case USER -> response.sendRedirect("/availableCars");
                case ADMIN -> response.sendRedirect("/manageCars");
            }

        } catch (WrongUserCredentialsException | DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }
}