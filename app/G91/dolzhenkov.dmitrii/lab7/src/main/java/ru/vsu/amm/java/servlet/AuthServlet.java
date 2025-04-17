package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.enums.UserRole;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.UserRequest;
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

@WebServlet(name = "AuthServlet", urlPatterns = "/signin")
public class AuthServlet extends HttpServlet {
    private static final String AUTH_VIEW  = "/signin.jsp";
    private static final String ADD_SCOOTER_VIEW = "/addScooter.jsp";
    private static final String MAIN_UI  = "/main.jsp";
    private final AuthService authService;

    public AuthServlet() {
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
            UserRole role = authService.login(new UserRequest(name, password));

            HttpSession session = request.getSession();
            session.setAttribute("login", name);
            switch (role) {
                case USER -> response.sendRedirect(MAIN_UI);
                case ADMIN -> response.sendRedirect(ADD_SCOOTER_VIEW);
                case null, default -> {
                    request.setAttribute(ErrorMessages.ERROR_MESSAGE, ErrorMessages.FIND_USER_ROLE);
                    getServletContext().getRequestDispatcher(AUTH_VIEW).forward(request, response);
                }
            }
        } catch (WrongUserCredentialsException | DataAccessException e) {
            request.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(AUTH_VIEW).forward(request, response);
        }
    }

    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(AUTH_VIEW).forward(request, response);
    }
}