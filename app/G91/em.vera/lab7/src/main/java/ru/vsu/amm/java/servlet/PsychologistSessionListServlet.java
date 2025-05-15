package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.utils.ServletConstants.PSY_SESSIONS_PAGE;
import static ru.vsu.amm.java.utils.ServletConstants.URL_PSY_LOGIN;
import static ru.vsu.amm.java.utils.ServletConstants.URL_PSY_SESSIONS;

@WebServlet(name = "PsychologistSessionListServlet", urlPatterns = URL_PSY_SESSIONS)
public class PsychologistSessionListServlet extends HttpServlet {
    private final SessionService sessionService;

    public PsychologistSessionListServlet() {
        this.sessionService = new SessionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("login") == null) {
            resp.sendRedirect(req.getContextPath() + URL_PSY_LOGIN);
            return;
        }
        String login = (String) httpSession.getAttribute("login");

        List<SessionDto> sessions = sessionService.getUpcomingSessionsByPsychologistLogin(login);

        req.setAttribute("sessions", sessions);
        getServletContext()
                .getRequestDispatcher(PSY_SESSIONS_PAGE)
                .forward(req, resp);
    }
}
