package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ServletConstants.*;

@WebServlet(name = "PsychologistLoginServlet", urlPatterns = URL_PSY_LOGIN)
public class PsychologistLoginServlet extends HttpServlet {

    private final PsychologistService psychologistService;

    public PsychologistLoginServlet() {
        this.psychologistService = new PsychologistServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(PSY_LOGIN_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login    = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            psychologistService.login(new PsychologistLoginRequest(login, password));

            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect(URL_PSY_SESSIONS);

        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext()
                    .getRequestDispatcher(PSY_LOGIN_PAGE)
                    .forward(req, resp);
        }
    }
}
