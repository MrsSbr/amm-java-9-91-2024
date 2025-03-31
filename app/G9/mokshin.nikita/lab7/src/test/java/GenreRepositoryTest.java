import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenreRepositoryTest {
    private static GenreRepository genreRepository;
    private static HikariDataSource dataSource;

    @BeforeAll
    static void setup() throws SQLException {
        System.setProperty("test", "true");
        dataSource = DatabaseConfig.getDataSource();
        genreRepository = new GenreRepository(dataSource);
    }

    @BeforeEach
    void cleanDatabase() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM genre");
    }

    @Test
    void testSaveAndFindById() {
        Genre genre = new Genre("Action");
        genreRepository.save(genre);

        long id = genre.getId();
        Optional<Genre> found = genreRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("Action", found.get().getTitle());
    }

    @Test
    void testFindAll() {
        genreRepository.save(new Genre("Comedy"));
        genreRepository.save(new Genre("Horror"));

        List<Genre> genres = genreRepository.findAll();
        assertEquals(2, genres.size());
    }

    @Test
    void testDelete() {
        Genre genre = new Genre("Horror");
        genreRepository.save(genre);

        genreRepository.delete(genre);
        Optional<Genre> deletedGenre = genreRepository.findById(genre.getId());

        assertFalse(deletedGenre.isPresent());
    }
}
