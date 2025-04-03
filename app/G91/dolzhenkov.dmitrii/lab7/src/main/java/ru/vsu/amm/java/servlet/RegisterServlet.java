package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.service.implementations.UserAuthManager;
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
        this.authService = new UserAuthManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAccessInterface(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            RegisterRequest registerRequest = new RegisterRequest(name, password);

            authService.register(registerRequest);
            HttpSession session = request.getSession();
            session.setAttribute("login", name);
            response.sendRedirect(MAIN_UI);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            request.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }

    }

    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
    }
}