package ru.vsu.amm.java.unit_tests;

import org.junit.Test;
import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.repository.ToyRepository;
import ru.vsu.amm.java.service.ToyService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestToyService {

    @Test
    public void testGetAllToys() {
        ToyService service = new ToyService();
        List<Toy> toys = service.getAllToys();
        assertTrue(toys.size() >= 0);
    }

    @Test
    public void testAddToy() throws SQLException {
        ToyService service = new ToyService();
        service.addToy("Test Toy", "25.50");

        ToyRepository toyRepository = new ru.vsu.amm.java.repository.ToyRepository();
        Optional<Toy> toy = toyRepository.findByName("Test Toy");
        assertTrue(toy.isPresent());

        toyRepository.delete(toy.get().getId());
    }
}
