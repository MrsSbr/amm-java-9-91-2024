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

@WebServlet("/update-incident")
public class UpdateIncidentServlet extends HttpServlet {
    private final IncidentService incidentService = new IncidentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        String description = req.getParameter("description");

        Incident updatedIncident = new Incident();
        updatedIncident.setIdIncident(id);
        updatedIncident.setDateOfIncident(date);
        updatedIncident.setDescription(description);

        incidentService.updateIncident(id, updatedIncident);
        resp.sendRedirect("incidents");
    }
}