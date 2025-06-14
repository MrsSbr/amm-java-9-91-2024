package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.services.clients.ClientService;
import ru.vsu.amm.java.services.clients.impl.ClientServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ApplicationConstants.CLIENT_BOOKINGS_URL;
import static ru.vsu.amm.java.utils.ApplicationConstants.REGISTER_PAGE;
import static ru.vsu.amm.java.utils.ApplicationConstants.REGISTER_URL;

@WebServlet(name = "ClientRegisterServlet", urlPatterns = REGISTER_URL)
public class ClientRegisterServlet extends HttpServlet {

    private final ClientService clientService;

    public ClientRegisterServlet() {
        this.clientService = new ClientServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(REGISTER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        try {
            clientService.register(new ClientRegisterRequest(name, email, password));

            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            resp.sendRedirect(CLIENT_BOOKINGS_URL);
        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher(REGISTER_URL).forward(req, resp);
        }
    }
}
