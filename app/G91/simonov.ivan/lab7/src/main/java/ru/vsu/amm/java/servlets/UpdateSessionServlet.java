package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.UpdateException;
import ru.vsu.amm.java.service.UpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateSession")
public class UpdateSessionServlet extends HttpServlet {

    private final UpdateService updateService;

    public UpdateSessionServlet() {

        updateService = new UpdateService();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            updateService.update(Integer.parseInt(request.getParameter("sessionId")));

            response.sendRedirect("viewSessions?message=Session updated successfully!");

        } catch (UpdateException e) {

            response.sendRedirect(String.format("viewSessions?error=%s", e.getMessage()));

        }

    }
}
