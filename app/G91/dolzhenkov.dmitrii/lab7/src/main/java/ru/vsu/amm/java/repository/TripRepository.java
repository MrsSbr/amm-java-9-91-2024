package ru.vsu.amm.java.repository;

import lombok.RequiredArgsConstructor;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.TripEntity;
import ru.vsu.amm.java.mappers.TripMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripRepository implements CrudRepository<TripEntity>{
    private final DataSource dataSource;

    public TripRepository()
    {
        this.dataSource= DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<TripEntity> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM Trip WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(TripMapper.ResultSetToUserCarEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<TripEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM Trip";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<TripEntity> usersCars = new ArrayList<>();
                while (resultSet.next()) {
                    usersCars.add(TripMapper.ResultSetToUserCarEntity(resultSet));
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
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getScooterId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
            preparedStatement.setDouble(5, entity.getStartLatitude());
            preparedStatement.setDouble(6, entity.getStartLongitude());
            preparedStatement.setDouble(7, entity.getEndLatitude());
            preparedStatement.setDouble(8, entity.getEndLongitude());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(TripEntity entity) throws SQLException {
        final String query = "UPDATE Trip SET user_id = ?, scooter_id = ?, start_time = ?, end_time = ?, " +
                "start_latitude = ?, start_longitude = ?, end_latitude = ?, end_longitude = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getScooterId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
            preparedStatement.setDouble(5, entity.getStartLatitude());
            preparedStatement.setDouble(6, entity.getStartLongitude());
            preparedStatement.setDouble(7, entity.getEndLatitude());
            preparedStatement.setDouble(8, entity.getEndLongitude());
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
