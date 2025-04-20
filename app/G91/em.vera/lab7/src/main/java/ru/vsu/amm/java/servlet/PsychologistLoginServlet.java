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
import ru.vsu.amm.java.services.PsychologistAuthService;
import ru.vsu.amm.java.services.impl.PsychologistAuthServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ServletConstants.HOME_PAGE;

@WebServlet(name = "PsychologistLoginServlet", urlPatterns = "/psychologist/login")
public class PsychologistLoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "/psychologistLogin.jsp";
    private final PsychologistAuthService authService;

    public PsychologistLoginServlet() {
        this.authService = new PsychologistAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(LOGIN_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login    = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            authService.login(new PsychologistLoginRequest(login, password));

            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect(HOME_PAGE);

        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext()
                    .getRequestDispatcher(LOGIN_PAGE)
                    .forward(req, resp);
        }
    }
}
