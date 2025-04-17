package ru.vsu.amm.java.repository.implementations;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserCarEntity;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.UserCarMapper;
import ru.vsu.amm.java.model.dto.CarDto;
import ru.vsu.amm.java.repository.interfaces.CrudRepository;
import ru.vsu.amm.java.utils.CarPriceUtil;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class UserCarRepository implements CrudRepository<UserCarEntity> {
    private final DataSource dataSource;

    public UserCarRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserCarEntity> findById(Long id) {
        final String query = "SELECT id, userId, carId, startTrip, endTrip, priceForMinute FROM user_car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserCarMapper.resultSetToUserCarEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_USER_CAR_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.FIND_USER_CAR_BY_ID + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserCarEntity> findAll() {
        final String query = "SELECT id, userId, carId, startTrip, endTrip, priceForMinute FROM user_car";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserCarEntity> usersCars = new ArrayList<>();
                while (resultSet.next()) {
                    usersCars.add(UserCarMapper.resultSetToUserCarEntity(resultSet));
                }
                return usersCars;
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_ALL_USER_CARS, e);
            throw new DataAccessException(ErrorMessages.FIND_ALL_USER_CARS, e);
        }
    }

    @Override
    public void save(UserCarEntity entity) {
        final String query = "INSERT INTO user_car(user_id, car_id, start_trip, end_trip, price_per_minute) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getCarId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTrip()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTrip()));
            preparedStatement.setBigDecimal(5, entity.getPriceForMinute());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.SAVE_USER_CAR, e);
            throw new DataAccessException(ErrorMessages.SAVE_USER_CAR, e);
        }
    }

    @Override
    public void update(UserCarEntity entity) {
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
        } catch (SQLException e) {
            log.error(ErrorMessages.UPDATE_USER_CAR, e);
            throw new DataAccessException(ErrorMessages.UPDATE_USER_CAR, e);
        }
    }

    @Override
    public void delete(Long id) {
        final String query = "DELETE FROM user_car WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.DELETE_USER_CAR_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.DELETE_USER_CAR_BY_ID + id, e);
        }
    }

    public void bookCar(Long userId, CarDto carDto) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM car WHERE id = ? FOR UPDATE")) {
                stmt.setLong(1, carDto.getId());
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    throw new NoSuchElementException(ErrorMessages.FIND_CAR_BY_ID + carDto.getId());
                }
                String status = rs.getString("status");
                if (!Status.AVAILABLE.toString().equals(status)) {
                    conn.rollback();
                    throw new IllegalStateException(ErrorMessages.CAR_ALREADY_RENTED + carDto.getId());
                }
            }
            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE car SET status = ? WHERE id = ?")) {
                updateStmt.setString(1, Status.RENTED.toString());
                updateStmt.setLong(2, carDto.getId());
                updateStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO user_car (user_id, car_id, start_time, end_time, price) VALUES (?, ?, ?, ?, ?)")) {
                insertStmt.setLong(1, userId);
                insertStmt.setLong(2, carDto.getId());
                insertStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                insertStmt.setTimestamp(4, null);
                insertStmt.setBigDecimal(5, CarPriceUtil.getCarPrice(carDto));
                insertStmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            log.error(ErrorMessages.BOOK_CAR + carDto.getId(), e);
            throw new DataAccessException(ErrorMessages.BOOK_CAR + carDto.getId(), e);
        }
    }
}