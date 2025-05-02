package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import ru.vsu.amm.java.utils.ServletConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static ru.vsu.amm.java.utils.SessionPriceUtil.getSessionPrice;

@WebServlet(name = "EditSessionServlet", urlPatterns = ServletConstants.URL_EDIT_SESSION)
public class EditSessionServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        HttpSession httpSession = req.getSession(false);

        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + determineRedirectList(httpSession));
            return;
        }

        Long sessionId = Long.parseLong(idParam);
        SessionDto sessionDto = sessionService.getSessionById(sessionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Session not found, id=" + sessionId)
                );

        req.setAttribute("session", sessionDto);
        getServletContext()
                .getRequestDispatcher(ServletConstants.PAGE_EDIT_SESSION)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null
                || (httpSession.getAttribute("email") == null
                && httpSession.getAttribute("login") == null)) {
            resp.sendRedirect(req.getContextPath() + ServletConstants.URL_CLIENT_LOGIN);
            return;
        }

        Long sessionId = Long.valueOf(req.getParameter("id"));
        LocalDate date   = LocalDate.parse(req.getParameter("date"));
        Short duration   = Short.valueOf(req.getParameter("duration"));
        BigDecimal price = getSessionPrice(duration);

        Optional<SessionDto> opt = sessionService.getSessionById(sessionId);
        if (opt.isPresent()) {
            SessionDto dto = opt.get();
            dto.setDate(date);
            dto.setDuration(duration);
            dto.setPrice(price);
            sessionService.updateSession(dto);
        }

        resp.sendRedirect(req.getContextPath() + determineRedirectList(httpSession));
    }

    private String determineRedirectList(HttpSession httpSession) {
        if (httpSession != null && httpSession.getAttribute("login") != null) {
            return ServletConstants.URL_PSY_SESSIONS;
        } else {
            return ServletConstants.URL_CLIENT_SESSIONS;
        }
    }
}
