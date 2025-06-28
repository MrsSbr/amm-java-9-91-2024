import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.repository.ToyRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestToyRepositoryIntegration {

    private ToyRepository toyRepository;

    @BeforeEach
    void setup() throws SQLException {
        toyRepository = new ToyRepository();
        toyRepository.setDataSource(DbConfig.getDataSourceForTest());

        try (Connection connection = DbConfig.getDataSourceForTest().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO toy (name, price) VALUES ('Teddy Bear', 15.99)");
            statement.execute("INSERT INTO toy (name, price) VALUES ('Car', 20.00)");
        }
    }

    @AfterEach
    public void clean() throws SQLException {
        DataSource dataSource = DbConfig.getDataSourceForTest();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM toy");
        }
    }

    @Test
    public void testFindByName() throws SQLException {
        Optional<Toy> toy = toyRepository.findByName("Teddy Bear");
        assertTrue(toy.isPresent());
        assertEquals("Teddy Bear", toy.get().getName());
        assertEquals(BigDecimal.valueOf(15.99), toy.get().getPrice());

    }

    @Test
    public void testSave() throws SQLException {
        Toy t = new Toy(null, "New Toy", BigDecimal.valueOf(10.99));
        toyRepository.save(t);
        Optional<Toy> toy = toyRepository.findByName("New Toy");
        assertTrue(toy.isPresent());
        assertEquals("New Toy", toy.get().getName());
        assertEquals(BigDecimal.valueOf(10.99), toy.get().getPrice());
    }

    @Test
    public void testFindAll() throws SQLException{
        List<Toy> toys = toyRepository.findAll();
        assertTrue(toys.size() >=0 );
    }

    @Test
    public void testDelete() throws SQLException{
        Toy t = new Toy(null, "ToyToDelete", BigDecimal.valueOf(5.50));
        toyRepository.save(t);
        Optional<Toy> toyToDelete = toyRepository.findByName("ToyToDelete");
        assertTrue(toyToDelete.isPresent());
        toyRepository.delete(toyToDelete.get().getId());
        Optional<Toy> deletedToy = toyRepository.findByName("ToyToDelete");
        assertFalse(deletedToy.isPresent());
    }

    @Test
    public void testFindById() throws SQLException{
        Toy t = new Toy(null, "ToyToFind", BigDecimal.valueOf(7.77));
        toyRepository.save(t);
        Optional<Toy> toyToFind = toyRepository.findByName("ToyToFind");
        assertTrue(toyToFind.isPresent());

        Optional<Toy> toy = toyRepository.findById(toyToFind.get().getId());
        assertTrue(toy.isPresent());
        assertEquals("ToyToFind", toy.get().getName());
        assertEquals(BigDecimal.valueOf(7.77), toy.get().getPrice());
    }
}
