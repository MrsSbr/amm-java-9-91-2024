package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.services.ClientAuthService;
import ru.vsu.amm.java.services.impl.ClientAuthServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ServletConstants.CLIENT_REGISTER_PAGE;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_REGISTER;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_SESSIONS;


@WebServlet(name = "ClientRegistrationServlet", urlPatterns = URL_CLIENT_REGISTER)
public class ClientRegistrationServlet extends HttpServlet {

    private final ClientAuthService authService;

    public ClientRegistrationServlet() {
        this.authService = new ClientAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(CLIENT_REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        try {
            authService.register(new ClientRegisterRequest(name, email, password));

            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            resp.sendRedirect(URL_CLIENT_SESSIONS);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher(CLIENT_REGISTER_PAGE).forward(req, resp);
        }
    }
}
