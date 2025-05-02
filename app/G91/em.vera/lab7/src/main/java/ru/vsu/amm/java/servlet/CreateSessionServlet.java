package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.services.ClientService;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.ClientServiceImpl;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static ru.vsu.amm.java.utils.ServletConstants.PAGE_CREATE_SESSION;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_LOGIN;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CLIENT_SESSIONS;
import static ru.vsu.amm.java.utils.ServletConstants.URL_CREATE_SESSION;
import static ru.vsu.amm.java.utils.SessionPriceUtil.getSessionPrice;

@WebServlet(name = "CreateSessionServlet", urlPatterns = URL_CREATE_SESSION)
public class CreateSessionServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();
    private final PsychologistService psychologistService = new PsychologistServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String psyLogin = req.getParameter("psychologistLogin");
        req.setAttribute("psychologistLogin", psyLogin);

        List<String> busyDates = sessionService
                .getUpcomingSessionsByPsychologistLogin(psyLogin)
                .stream()
                .map(x -> x.getDate().toString())
                .toList();

        String[] busyDatesArr = busyDates.toArray(new String[0]);
        req.setAttribute("busyDates", busyDatesArr);

        getServletContext()
                .getRequestDispatcher(PAGE_CREATE_SESSION)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + URL_CLIENT_LOGIN);
            return;
        }

        String email = (String) httpSession.getAttribute("email");
        String login = req.getParameter("login");
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        Short duration = Short.valueOf(req.getParameter("duration"));
        BigDecimal price = getSessionPrice(duration);

        Long clientId = clientService
                .getClientByEmail(email)
                .orElseThrow()
                .getIdClient();

        Long psychologistId = psychologistService
                .getPsychologistByLogin(login)
                .orElseThrow()
                .getIdPsychologist();

        SessionDto dto = SessionDto.builder()
                .idClient(clientId)
                .idPsychologist(psychologistId)
                .date(date)
                .duration(duration)
                .price(price)
                .build();

        sessionService.createSession(dto);

        resp.sendRedirect(req.getContextPath() + URL_CLIENT_SESSIONS);
    }
}
