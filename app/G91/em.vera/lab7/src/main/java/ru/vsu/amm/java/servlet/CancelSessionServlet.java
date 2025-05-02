package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import ru.vsu.amm.java.utils.ServletConstants;

import java.io.IOException;

@WebServlet(name = "CancelSessionServlet", urlPatterns = ServletConstants.URL_CLIENT_CANCEL_SESSION)
public class CancelSessionServlet extends HttpServlet {
    private final SessionService sessionService = new SessionServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null
                || httpSession.getAttribute("email") == null && httpSession.getAttribute("login") == null) {
            resp.sendRedirect(req.getContextPath()
                    + ServletConstants.URL_CLIENT_LOGIN);
            return;
        }

        String idParam = req.getParameter("id");

        Long sessionId = Long.valueOf(idParam);
        sessionService.cancelSession(sessionId);

        String redirect = (httpSession.getAttribute("login") != null)
                ? ServletConstants.URL_PSY_SESSIONS
                : ServletConstants.URL_CLIENT_SESSIONS;

        resp.sendRedirect(req.getContextPath() + redirect);
    }
}
