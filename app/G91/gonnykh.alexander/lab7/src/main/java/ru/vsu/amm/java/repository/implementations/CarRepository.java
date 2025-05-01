package ru.vsu.amm.java.repository.implementations;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.CarEntity;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.CarMapper;
import ru.vsu.amm.java.repository.interfaces.CrudRepository;
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
public class CarRepository implements CrudRepository<CarEntity> {
    private final DataSource dataSource;

    public CarRepository() {
        this.dataSource = DatabaseConfiguration.getMainDataSource();
    }

    @Override
    public Optional<CarEntity> findById(Long id) {
        final String query = "SELECT id, manufacturer, model, year, status, car_class FROM car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(CarMapper.resultSetToCarEntity(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_CAR_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.FIND_CAR_BY_ID + id, e);
        }
    }

    @Override
    public List<CarEntity> findAll() {
        final String query = "SELECT id, manufacturer, model, year, status, car_class FROM car";
        return getCarEntities(query);
    }

    @Override
    public void save(CarEntity entity) {
        final String query = "INSERT INTO car(manufacturer, model, year, status, car_class) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getManufacturer());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.setString(5, entity.getCarClass().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.SAVE_CAR, e);
            throw new DataAccessException(ErrorMessages.SAVE_CAR, e);
        }
    }

    @Override
    public void update(CarEntity entity) {
        final String query = """
                   UPDATE car SET manufacturer = ?, model = ?, year = ?, status = ?,
                car_class = ? WHERE id = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getManufacturer());
            preparedStatement.setString(2, entity.getModel());
            preparedStatement.setInt(3, entity.getYear());
            preparedStatement.setString(4, entity.getStatus().toString());
            preparedStatement.setString(5, entity.getCarClass().toString());
            preparedStatement.setLong(6, entity.getId());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.UPDATE_CAR + entity, e);
            throw new DataAccessException(ErrorMessages.UPDATE_CAR, e);
        }
    }

    @Override
    public void delete(Long id) {
        final String query = "DELETE FROM car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.DELETE_CAR_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.DELETE_CAR_BY_ID + id, e);
        }
    }

    public List<CarEntity> findByStatus(Status status) {
        final String query = "SELECT id, manufacturer, model, year, status, car_class FROM car WHERE status = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(status));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<CarEntity> cars = new ArrayList<>();
                while (resultSet.next()) {
                    cars.add(CarMapper.resultSetToCarEntity(resultSet));
                }
                return cars;
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_ALL_CARS, e);
            throw new DataAccessException(ErrorMessages.FIND_ALL_CARS, e);
        }
    }

    public List<CarEntity> findNotRented() {
        final String query = """
                SELECT id, manufacturer, model, year, status, car_class FROM car
                WHERE status IN ('AVAILABLE','BROKEN')
                """;
        return getCarEntities(query);
    }


    private List<CarEntity> getCarEntities(String query) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<CarEntity> cars = new ArrayList<>();
                while (resultSet.next()) {
                    cars.add(CarMapper.resultSetToCarEntity(resultSet));
                }
                return cars;
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_ALL_CARS, e);
            throw new DataAccessException(ErrorMessages.FIND_ALL_CARS, e);
        }
    }

}
