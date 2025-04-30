package ru.vsu.amm.java.unit_tests;

import org.junit.Test;
import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.repository.ToyRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestToyRepository {

    @Test
    public void testFindByName() throws SQLException {
        ToyRepository repo = new ToyRepository();
        Optional<Toy> toy = repo.findByName("Teddy Bear");
        assertTrue(toy.isPresent());
    }

    @Test
    public void testSave() throws SQLException {
        ToyRepository repo = new ToyRepository();
        Toy t = new Toy(null, "New Toy", BigDecimal.valueOf(10.99));
        repo.save(t);
        Optional<Toy> toy = repo.findByName("New Toy");
        assertTrue(toy.isPresent());
    }

    @Test
    public void testFindAll() throws SQLException{
        ToyRepository repo = new ToyRepository();
        List<Toy> toys = repo.findAll();
        assertTrue(toys.size() >=0 );
    }

    @Test
    public void testDelete() throws SQLException{
        ToyRepository repo = new ToyRepository();
        repo.delete(1L);

    }

    @Test
    public void testFindById() throws SQLException{
        ToyRepository repo = new ToyRepository();
        Optional<Toy> toy = repo.findById(1L);
        assertTrue(toy.isPresent());
    }
}
