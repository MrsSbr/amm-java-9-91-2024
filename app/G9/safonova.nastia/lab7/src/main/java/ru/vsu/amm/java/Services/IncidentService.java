package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Incident;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.DinoRepository;
import ru.vsu.amm.java.Repository.EmployeeRepository;
import ru.vsu.amm.java.Repository.IncidentRepository;

import java.sql.SQLException;
import java.util.List;

public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final EmployeeRepository employeeRepository;
    private final DinoRepository dinoRepository;

    public IncidentService() {
        this.incidentRepository = new IncidentRepository();
        this.employeeRepository = new EmployeeRepository();
        this.dinoRepository = new DinoRepository();
    }

    public Incident createIncident(Long emplId, Long dinoId, Incident incident) {
        validateRelationsExist(emplId, dinoId);

        try {
            incident.setEmplId(emplId);
            incident.setDinoId(dinoId);
            incidentRepository.save(incident);
            return incident;
        } catch (SQLException e) {
            throw new DbException("Error creating incident", e);
        }
    }

    public List<Incident> getAllIncidents() {
        try {
            return incidentRepository.findAll();
        } catch (SQLException e) {
            throw new DbException("Error fetching incidents", e);
        }
    }

    public Incident getIncidentById(Long id) {
        try {
            return incidentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Incident not found"));
        } catch (SQLException e) {
            throw new DbException("Error fetching incident", e);
        }
    }

    public void updateIncident(Long id, Incident updatedIncident) {
        try {
            Incident existing = incidentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Incident not found"));

            existing.setDateOfIncident(updatedIncident.getDateOfIncident());
            existing.setDescription(updatedIncident.getDescription());

            incidentRepository.update(existing);
        } catch (SQLException e) {
            throw new DbException("Error updating incident", e);
        }
    }

    public void deleteIncident(Long id) {
        try {
            Incident incident = incidentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Incident not found"));
            incidentRepository.delete(incident);
        } catch (SQLException e) {
            throw new DbException("Error deleting incident", e);
        }
    }

    private void validateRelationsExist(Long emplId, Long dinoId) {
        try {
            if (!employeeRepository.findById(emplId).isPresent()) {
                throw new NotFoundException("Employee not found");
            }
            if (!dinoRepository.findById(dinoId).isPresent()) {
                throw new NotFoundException("Dino not found");
            }
        } catch (SQLException e) {
            throw new DbException("Error validating relations", e);
        }
    }
}