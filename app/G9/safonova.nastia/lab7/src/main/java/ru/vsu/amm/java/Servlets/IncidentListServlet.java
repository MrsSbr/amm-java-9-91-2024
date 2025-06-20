package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Services.IncidentService;
import ru.vsu.amm.java.Entities.Incident;
import ru.vsu.amm.java.Exception.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/incidents")
public class IncidentListServlet extends HttpServlet {
    private final IncidentService incidentService = new IncidentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Incident> incidents = incidentService.getAllIncidents();
            req.setAttribute("incidents", incidents);
            req.getRequestDispatcher("/incidents.jsp").forward(req, resp);
        } catch (DbException e) {
            req.setAttribute("error", "Error with data loading");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}