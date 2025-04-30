package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.repository.ToyRepository;

import java.sql.SQLException;
import java.util.List;

public class ToyService {
    private final ToyRepository toyRepository;

    public ToyService() {
        toyRepository = new ToyRepository();
    }

    public List<Toy> getAllToys() {
        try {
            return toyRepository.findAll();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
