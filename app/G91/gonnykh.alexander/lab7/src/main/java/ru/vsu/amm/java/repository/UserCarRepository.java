package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserCarEntity;
import ru.vsu.amm.java.mappers.UserCarMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCarRepository implements CrudRepository<UserCarEntity> {
    private final DataSource dataSource;

    public UserCarRepository(DataSource dataSource) {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserCarEntity> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM user_car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserCarMapper.ResultSetToUserCarEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<UserCarEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM user_car";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserCarEntity> usersCars = new ArrayList<>();
                while (resultSet.next()) {
                    usersCars.add(UserCarMapper.ResultSetToUserCarEntity(resultSet));
                }
                return usersCars;
            }
        }
    }

    @Override
    public void save(UserCarEntity entity) throws SQLException {
        final String query = "INSERT INTO user_car(user_id, car_id, start_trip, end_trip, price_per_minute) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getCarId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTrip()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTrip()));
            preparedStatement.setBigDecimal(5, entity.getPriceForMinute());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(UserCarEntity entity) throws SQLException {
        final String query = "UPDATE user_car SET user_id = ?, car_id = ?, start_trip = ?, end_trip = ?, price_per_minute = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getCarId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTrip()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTrip()));
            preparedStatement.setBigDecimal(5, entity.getPriceForMinute());
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM user_car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
