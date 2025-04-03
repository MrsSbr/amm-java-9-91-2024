package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.LoginRequest;
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

@WebServlet(name = "AuthController", urlPatterns = "/signin")
public class AuthController extends HttpServlet {

    private static final String AUTH_VIEW  = "/signin.jsp";
    private static final String MAIN_UI  = "/index.jsp";
    private final AuthService authService;

    public AuthController() {
        this.authService = new DefaultAuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAccessInterface(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            authService.login(new LoginRequest(name, password));

            HttpSession session = req.getSession();
            session.setAttribute("login", name);
            resp.sendRedirect(MAIN_UI );
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(AUTH_VIEW).forward(req, resp);
        }
    }

    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(AUTH_VIEW).forward(request, response);
    }
}