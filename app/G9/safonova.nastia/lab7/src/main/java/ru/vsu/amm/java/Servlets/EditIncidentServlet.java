package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.Incident;
import ru.vsu.amm.java.Services.DinoService;
import ru.vsu.amm.java.Services.EmployeeService;
import ru.vsu.amm.java.Services.IncidentService;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/edit-incident")
public class EditIncidentServlet extends HttpServlet {
    private final IncidentService incidentService = new IncidentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Incident incident = incidentService.getIncidentById(id);
            req.setAttribute("incident", incident);
            req.getRequestDispatcher("/edit_incident.jsp").forward(req, resp);
        } catch (NotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");

            Long id = Long.parseLong(req.getParameter("id"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            String description = req.getParameter("description");

            Incident updatedIncident = new Incident();
            updatedIncident.setIdIncident(id);
            updatedIncident.setDateOfIncident(date);
            updatedIncident.setDescription(description);

            incidentService.updateIncident(id, updatedIncident);
            resp.sendRedirect("incidents");
        } catch (DbException | NotFoundException e) {
            req.setAttribute("error", "Ошибка обновления: " + e.getMessage());
            req.getRequestDispatcher("/edit_incident.jsp").forward(req, resp);
        }
    }
}