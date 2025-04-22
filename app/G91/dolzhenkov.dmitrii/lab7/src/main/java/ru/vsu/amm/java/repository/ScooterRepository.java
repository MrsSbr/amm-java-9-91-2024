package ru.vsu.amm.java.repository;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.ScooterEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.ScooterMapper;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ScooterRepository implements CrudRepository<ScooterEntity>{
    private final DataSource dataSource;

    public ScooterRepository()
    {
        this.dataSource= DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<ScooterEntity> findById(Long id) {
        final String query = "SELECT model, latitude, longitude, status  FROM Scooter WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ScooterMapper.ResultSetToCarEntity(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_SCOOTER_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.FIND_SCOOTER_BY_ID + id, e);
        }
    }

    @Override
    public List<ScooterEntity> findAll() {
        final String query = "SELECT id, model, latitude, longitude, status FROM Scooter";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ScooterEntity> cars = new ArrayList<>();
                while (resultSet.next()) {
                    cars.add(ScooterMapper.ResultSetToCarEntity(resultSet));
                }
                return cars;
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_ALL_SCOOTER, e);
            throw new DataAccessException(ErrorMessages.FIND_ALL_SCOOTER, e);
        }
    }

    @Override
    public void save(ScooterEntity entity) {
        final String query = "INSERT INTO Scooter(model, latitude, longitude, status) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getModel());
            preparedStatement.setDouble(2, entity.getLatitude());
            preparedStatement.setDouble(3, entity.getLongitude());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.SAVE_SCOOTER, e);
            throw new DataAccessException(ErrorMessages.SAVE_SCOOTER, e);
        }
    }

    @Override
    public void update(ScooterEntity entity) {
        final String query = "UPDATE Scooter SET model = ?, latitude = ?, longitude = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getModel());
            preparedStatement.setDouble(2, entity.getLatitude());
            preparedStatement.setDouble(3, entity.getLongitude());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.UPDATE_SCOOTER + entity, e);
            throw new DataAccessException(ErrorMessages.UPDATE_SCOOTER, e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM Scooter WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.DELETE_SCOOTER_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.DELETE_SCOOTER_BY_ID, e);
        }
    }
}
