package ru.vsu.amm.java.repository;

import lombok.RequiredArgsConstructor;
import ru.vsu.amm.java.entities.ScooterEntity;
import ru.vsu.amm.java.mappers.ScooterMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ScooterRepository implements CrudRepository<ScooterEntity>{
    private final DataSource dataSource;

    @Override
    public Optional<ScooterEntity> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM Scooter WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ScooterMapper.ResultSetToCarEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<ScooterEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM Scooter";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ScooterEntity> cars = new ArrayList<>();
                while (resultSet.next()) {
                    cars.add(ScooterMapper.ResultSetToCarEntity(resultSet));
                }
                return cars;
            }
        }
    }

    @Override
    public void save(ScooterEntity entity) throws SQLException {
        final String query = "INSERT INTO Scooter(model, latitude, longitude, status) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getModel());
            preparedStatement.setDouble(2, entity.getLatitude());
            preparedStatement.setDouble(3, entity.getLongitude());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(ScooterEntity entity) throws SQLException {
        final String query = "UPDATE Scooter SET model = ?, latitude = ?, longitude = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getModel());
            preparedStatement.setDouble(2, entity.getLatitude());
            preparedStatement.setDouble(3, entity.getLongitude());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM Scooter WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
