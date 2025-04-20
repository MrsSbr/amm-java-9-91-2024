package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.services.ClientAuthService;
import ru.vsu.amm.java.services.impl.ClientAuthServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ServletConstants.HOME_PAGE;

@WebServlet(name = "ClientLoginServlet", urlPatterns = "/client/login")
public class ClientLoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "/clientlogin.jsp";
    private final ClientAuthService authService;

    public ClientLoginServlet() {
        this.authService = new ClientAuthServiceImpl();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            authService.login(new ClientLoginRequest(email, password));

            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            resp.sendRedirect(HOME_PAGE);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}
