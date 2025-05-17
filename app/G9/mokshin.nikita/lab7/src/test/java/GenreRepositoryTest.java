import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenreRepositoryTest {
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        genreRepository = new GenreRepository(dataSource);
    }

    @Test
    void testSave() throws Exception {
        Genre genre = new Genre("Action");

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);

        genreRepository.save(genre);

        verify(preparedStatement).setString(1, "Action");
        verify(preparedStatement).executeQuery();

        assertEquals(1L, genre.getId());
    }

    @Test
    void testFindById() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Action");

        Optional<Genre> result = genreRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Action", result.get().getTitle());
    }

    @Test
    void testFindAll() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("title")).thenReturn("Comedy", "Horror");

        List<Genre> genres = genreRepository.findAll();

        assertEquals(2, genres.size());
        assertEquals("Comedy", genres.get(0).getTitle());
        assertEquals("Horror", genres.get(1).getTitle());
    }

    @Test
    void testDelete() throws Exception {
        Genre genre = new Genre("Horror");
        genre.setId(1L);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        genreRepository.delete(genre);

        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).executeUpdate();
    }
}