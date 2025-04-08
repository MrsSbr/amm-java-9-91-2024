package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.exceptions.DeleteException;
import ru.vsu.amm.java.repository.SessionRepository;
import ru.vsu.amm.java.service.DeleteService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteSession")
public class DeleteSessionServlet extends HttpServlet {

    private final DeleteService deleteService;

    public DeleteSessionServlet() {

        deleteService = new DeleteService();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            deleteService.deleteSession(Integer.parseInt(request.getParameter("sessionId")));

            response.sendRedirect("viewSessions?message=Session deleted successfully!");

        } catch (DeleteException e) {

            response.sendRedirect(String.format("viewSessions?error=%s", e.getMessage()));

        }

    }
}
