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

@WebServlet("/add_incident")
public class AddIncidentServlet extends HttpServlet {
    private final IncidentService incidentService = new IncidentService();
    private final EmployeeService employeeService = new EmployeeService();
    private final DinoService dinoService = new DinoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("employees", employeeService.getAllEmployees());
            req.setAttribute("dinos", dinoService.getAllDinos());
            req.getRequestDispatcher("/add_incident.jsp").forward(req, resp);
        } catch (DbException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");

            Incident incident = new Incident();
            incident.setEmplId(Long.parseLong(req.getParameter("emplId")));
            incident.setDinoId(Long.parseLong(req.getParameter("dinoId")));
            incident.setDateOfIncident(LocalDate.parse(req.getParameter("date")));
            incident.setDescription(req.getParameter("description"));

            incidentService.createIncident(
                    incident.getEmplId(),
                    incident.getDinoId(),
                    incident
            );

            resp.sendRedirect("incidents");
        } catch (DbException | NotFoundException | NumberFormatException e) {
            req.setAttribute("error", "Error with creating: " + e.getMessage());
            req.getRequestDispatcher("/add_incident.jsp").forward(req, resp);
        }
    }
}