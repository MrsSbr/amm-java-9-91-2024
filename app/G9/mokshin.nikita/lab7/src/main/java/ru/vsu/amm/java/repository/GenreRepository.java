package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.Genre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreRepository implements CrudRepository<Genre> {
    private final DataSource dataSource;

    public GenreRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Genre> findById(long id) {
        String sql = "SELECT id, title FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getLong("id"),
                        resultSet.getString("title"));
                return Optional.of(genre);
            }
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() {
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

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return genres;
    }

    @Override
    public void save(Genre genre) {
        String sql = genre.getId() == null ? "INSERT INTO genre (title) VALUES (?)" : "UPDATE genre SET title = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, genre.getTitle());

            if (genre.getId() == null) {
                statement.executeUpdate();
            } else {
                statement.setLong(2, genre.getId());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(Genre genre) {
        String sql = "DELETE FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, genre.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }
}
