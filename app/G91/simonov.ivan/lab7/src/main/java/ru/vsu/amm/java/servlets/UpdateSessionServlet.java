package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.repository.SessionRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/updateSession")
public class UpdateSessionServlet extends HttpServlet {

    private SessionRepository sessionRepository = new SessionRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int sessionId = Integer.parseInt(request.getParameter("sessionId"));

        Optional<Session> repSession = sessionRepository.getById(sessionId);

        if (repSession.isPresent()) {

            repSession.get().setExitDate(LocalDateTime.now());
            sessionRepository.update(repSession.get());
            response.sendRedirect("userSessions.jsp?message=Session upgraded successfully!");

        } else {

            response.sendRedirect("userSessions.jsp?error=Session not found!");

        }

    }
}
