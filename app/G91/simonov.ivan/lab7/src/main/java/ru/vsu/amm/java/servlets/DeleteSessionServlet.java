package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.repository.SessionRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteSession")
public class DeleteSessionServlet extends HttpServlet {

    private SessionRepository sessionRepository = new SessionRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Session session = (Session) request.getAttribute("session");

        sessionRepository.delete(session);

        response.sendRedirect("userSessions.jsp?message=Session deleted successfully!");

    }
}
