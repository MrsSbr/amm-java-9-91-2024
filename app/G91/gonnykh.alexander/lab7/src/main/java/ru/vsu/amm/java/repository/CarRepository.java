package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.CarEntity;
import ru.vsu.amm.java.mappers.CarMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepository implements CrudRepository<CarEntity> {
    private final DataSource dataSource;

    public CarRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<CarEntity> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(CarMapper.ResultSetToCarEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<CarEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM car";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<CarEntity> cars = new ArrayList<>();
                while (resultSet.next()) {
                    cars.add(CarMapper.ResultSetToCarEntity(resultSet));
                }
                return cars;
            }
        }
    }

    @Override
    public void save(CarEntity entity) throws SQLException {
        final String query = "INSERT INTO car(manufacturer, model, year, status) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getManufacturer());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(CarEntity entity) throws SQLException {
        final String query = "UPDATE car SET manufacturer = ?, model = ?, year = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getManufacturer());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
