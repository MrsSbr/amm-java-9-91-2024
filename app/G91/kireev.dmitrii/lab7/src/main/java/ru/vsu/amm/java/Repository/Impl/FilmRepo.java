package ru.vsu.amm.java.Repository.Impl;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.Mapper.Impl.FilmMapper;
import ru.vsu.amm.java.Model.Entity.FilmEntity;
import ru.vsu.amm.java.Repository.Interface.CrudRepo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FilmRepo implements CrudRepo<FilmEntity> {
    private final DataSource dataSource;
    private final FilmMapper filmMapper;

    @Override
    public Optional<FilmEntity> findById(Long id) throws SQLException {
        final String query = "SELECT filmId, name, genre, duration, screenWriter, rating FROM film WHERE filmId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(filmMapper.resultSetToEntity(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FilmEntity> findAll() throws SQLException {
        final String query = "SELECT filmId, name, genre, duration, screenWriter, rating FROM film";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<FilmEntity> films = new ArrayList<>();
                while (resultSet.next()) {
                    films.add(filmMapper.resultSetToEntity(resultSet));
                }
                return films;
            }
        }
    }

    @Override
    public void save(FilmEntity entity) throws SQLException {
        final String query = "INSERT INTO film(name, genre, duration, screenWriter, rating) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getGenre());
            preparedStatement.setObject(3, entity.getDuration());
            preparedStatement.setString(4, entity.getScreenWriter());
            preparedStatement.setDouble(5, entity.getRating());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setFilmId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(FilmEntity entity) throws SQLException {
        final String query = "UPDATE film SET name = ?, genre = ?, duration = ?, screenWriter = ?, rating = ? WHERE filmId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getGenre());
            preparedStatement.setObject(3, entity.getDuration());
            preparedStatement.setString(4, entity.getScreenWriter());
            preparedStatement.setDouble(5, entity.getRating());
            preparedStatement.setLong(6, entity.getFilmId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM film WHERE filmId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
