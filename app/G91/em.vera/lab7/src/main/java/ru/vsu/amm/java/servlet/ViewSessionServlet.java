package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.models.dto.ClientDto;
import ru.vsu.amm.java.models.dto.PsychologistDto;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.services.ClientService;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.ClientServiceImpl;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import ru.vsu.amm.java.utils.ServletConstants;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ViewSessionServlet", urlPatterns = ServletConstants.URL_SESSION_VIEW)
public class ViewSessionServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();
    private final PsychologistService psychologistService = new PsychologistServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + ServletConstants.URL_CLIENT_SESSIONS);
            return;
        }
        Long sessionId;
        try {
            sessionId = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + ServletConstants.URL_CLIENT_SESSIONS);
            return;
        }
        SessionDto sessionDto = sessionService.getSessionById(sessionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Session not found, id=" + sessionId)
                );

        PsychologistDto psychologistDto = psychologistService
                .getPsychologistById(sessionDto.getIdPsychologist())
                .orElseThrow(() ->
                        new IllegalArgumentException("Session not found, idPsychologist=" + sessionId)
                );
        ClientDto clientDto = clientService
                .getClientById(sessionDto.getIdClient())
                .orElseThrow(() ->
                        new IllegalArgumentException("Session not found, idClient=" + sessionId)
                );

        req.setAttribute("session", sessionDto);
        req.setAttribute("psychologistName",
                psychologistDto != null
                        ? psychologistDto.getName() + " " + psychologistDto.getSurname()
                        : "—");
        req.setAttribute("clientName",
                clientDto != null
                        ? clientDto.getName()
                        : "—");
        req.setAttribute("clientEmail",
                clientDto != null
                        ? clientDto.getEmail()
                        : "—");

        getServletContext()
                .getRequestDispatcher(ServletConstants.SESSION_VIEW_PAGE)
                .forward(req, resp);
    }
}
