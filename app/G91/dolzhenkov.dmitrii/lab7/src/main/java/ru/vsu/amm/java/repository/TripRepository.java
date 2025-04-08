package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.TripEntity;
import ru.vsu.amm.java.mappers.TripMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.vsu.amm.java.mappers.TripMapper.setPreparedStatement;

public class TripRepository implements CrudRepository<TripEntity>{
    private final DataSource dataSource;

    public TripRepository()
    {
        this.dataSource= DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<TripEntity> findById(Long id) throws SQLException {
        final String query = "SELECT user_id, scooter_id, start_time, end_time, start_latitude, " +
                "start_longitude, end_latitude, end_longitude FROM Trip WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(TripMapper.resultSetToUserCarEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<TripEntity> findAll() throws SQLException {
        final String query = "SELECT id, user_id, scooter_id, start_time, end_time, start_latitude, " +
                "start_longitude, end_latitude, end_longitude FROM Trip";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<TripEntity> usersCars = new ArrayList<>();
                while (resultSet.next()) {
                    usersCars.add(TripMapper.resultSetToUserCarEntity(resultSet));
                }
                return usersCars;
            }
        }
    }

    @Override
    public void save(TripEntity entity) throws SQLException {
        final String query = "INSERT INTO Trip(user_id, scooter_id, start_time, end_time, start_latitude, " +
                "start_longitude, end_latitude, end_longitude) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(TripEntity entity) throws SQLException {
        final String query = "UPDATE Trip SET user_id = ?, scooter_id = ?, start_time = ?, end_time = ?, " +
                "start_latitude = ?, start_longitude = ?, end_latitude = ?, end_longitude = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM Trip WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
