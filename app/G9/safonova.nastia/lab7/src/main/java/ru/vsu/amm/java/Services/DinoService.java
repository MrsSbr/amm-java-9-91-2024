package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Dino;
import ru.vsu.amm.java.Enums.Kind;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.DinoRepository;

import java.sql.SQLException;
import java.util.List;

public class DinoService {
    private final DinoRepository dinoRepository;

    public DinoService() {
        this.dinoRepository = new DinoRepository();
    }

    public Dino createDino(Dino dino) {
        try {
            validateKind(dino.getKindOfDino());
            dinoRepository.save(dino);
            return dino;
        } catch (SQLException e) {
            throw new DbException("Error creating dino", e);
        }
    }

    public List<Dino> getAllDinos() {
        try {
            return dinoRepository.findAll();
        } catch (SQLException e) {
            throw new DbException("Error fetching dinos", e);
        }
    }

    public Dino getDinoById(Long id) {
        try {
            return dinoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dino not found"));
        } catch (SQLException e) {
            throw new DbException("Error fetching dino", e);
        }
    }

    public void updateDino(Long id, Dino updatedDino) {
        try {
            Dino existing = dinoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dino not found"));

            validateKind(updatedDino.getKindOfDino());
            updatedDino.setIdDino(id);
            dinoRepository.update(updatedDino);
        } catch (SQLException e) {
            throw new DbException("Error updating dino", e);
        }
    }

    public void deleteDino(Long id) {
        try {
            Dino dino = dinoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dino not found"));
            dinoRepository.delete(dino);
        } catch (SQLException e) {
            throw new DbException("Error deleting dino", e);
        }
    }

    private void validateKind(Kind kind) {
        try {
            Kind.valueOf(kind.name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dino kind: " + kind);
        }
    }
}