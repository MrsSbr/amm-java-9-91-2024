package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.repository.ToyRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public void addToy(String name, String price) throws SQLException {
        try {
            ToyRepository toyRepository = new ToyRepository();
            Optional<Toy> toyOptional = toyRepository.findByName(name);

            if (toyOptional.isEmpty()) {
                Toy toy = new Toy(null, name, Double.valueOf(price) != null ? java.math.BigDecimal.valueOf(Double.valueOf(price)) : null);
                toyRepository.save(toy);
            } else {
                throw new IllegalArgumentException("Игрушка уже существует");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteToy(String toyId) throws SQLException{
        try {
            ToyRepository toyRepository = new ToyRepository();
            Optional<Toy> toyOptional = toyRepository.findById(Long.valueOf(toyId));

            if (toyOptional.isPresent()) {
                toyRepository.delete(Long.valueOf(toyId));
            } else {
                throw new IllegalArgumentException("Игрушка не существует");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}