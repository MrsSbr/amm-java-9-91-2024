package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.Genre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreRepository implements CrudRepository<Genre> {
    private static final Logger logger = LoggerFactory.getLogger(GenreRepository.class);
    private final DataSource dataSource;

    public GenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Genre> findById(long id) {
        logger.info("Find genre with id={}", id);
        String sql = "SELECT id, title FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getLong("id"),
                        resultSet.getString("title"));
                logger.info("Genre found: {}", genre);
                return Optional.of(genre);
            }
        } catch (SQLException e) {
            logger.error("Error finding genre with id={}: {}", id, e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() {
        logger.info("Find all genres");
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT id, title FROM genre";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getLong("id"),
                        resultSet.getString("title"));
                genres.add(genre);
            }
            logger.info("Found {} genres", genres.size());
        } catch (SQLException e) {
            logger.error("Error found all genres: {}", e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return genres;
    }

    @Override
    public void save(Genre genre) {
        logger.info("Saving genre: {}", genre);
        String sql = genre.getId() == null ? "INSERT INTO genre (title) VALUES (?) RETURNING id" : "UPDATE genre SET title = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, genre.getTitle());

            if (genre.getId() != null) {
                statement.setLong(2, genre.getId());
                statement.executeUpdate();
            } else {
                statement.executeQuery();
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    genre.setId(resultSet.getLong("id"));
                }
            }

            logger.info("Genre saved successfully: {}", genre);
        } catch (SQLException e) {
            logger.error("Error saving genre {}: {}", genre, e.getMessage());
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(Genre genre) {
        logger.info("Deleting genre with id={}", genre.getId());
        String sql = "DELETE FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, genre.getId());

            statement.executeUpdate();
            logger.info("Genre with id={} deleted successfully", genre.getId());
        } catch (SQLException e) {
            logger.error("Error deleting genre with id={}: {}", genre.getId(), e.getMessage());
            throw new SqlException(e.getMessage());
        }
    }
}
