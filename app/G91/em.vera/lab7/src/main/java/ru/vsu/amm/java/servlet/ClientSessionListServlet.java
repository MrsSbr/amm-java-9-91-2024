package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.utils.ServletConstants.CLIENT_SESSIONS_PAGE;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_LOGIN;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_SESSIONS;

@WebServlet(name = "ClientSessionListServlet", urlPatterns = URL_CLIENT_SESSIONS)
public class ClientSessionListServlet extends HttpServlet {
    private final SessionService sessionService;


    public ClientSessionListServlet() {
        this.sessionService = new SessionServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + URL_CLIENT_LOGIN);
            return;
        }
        String email = (String) httpSession.getAttribute("email");


        List<SessionDto> sessions = sessionService.getUpcomingSessionsByClientEmail(email);

        req.setAttribute("sessions", sessions);
        getServletContext()
                .getRequestDispatcher(CLIENT_SESSIONS_PAGE)
                .forward(req, resp);
    }
}
