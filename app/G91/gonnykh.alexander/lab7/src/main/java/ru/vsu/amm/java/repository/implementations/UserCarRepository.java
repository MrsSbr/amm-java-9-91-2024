package ru.vsu.amm.java.repository.implementations;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserCarEntity;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.UserCarMapper;
import ru.vsu.amm.java.model.dto.CarDto;
import ru.vsu.amm.java.model.dto.TripDto;
import ru.vsu.amm.java.repository.interfaces.CrudRepository;
import ru.vsu.amm.java.utils.CarPriceUtil;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class UserCarRepository implements CrudRepository<UserCarEntity> {
    private final DataSource dataSource;

    public UserCarRepository() {
        this.dataSource = DatabaseConfiguration.getMainDataSource();
    }

    @Override
    public Optional<UserCarEntity> findById(Long id) {
        final String query = "SELECT id, user_id, car_id, start_trip, end_trip, price_per_minute, final_price FROM user_car WHERE id = ?";
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
        final String query = "SELECT id, user_id, car_id, start_trip, end_trip, price_per_minute, final_price FROM user_car";
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
        final String query = "INSERT INTO user_car(user_id, car_id, start_trip, end_trip, price_per_minute, final_price) VALUES(?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getCarId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTrip()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTrip()));
            preparedStatement.setBigDecimal(5, entity.getPriceForMinute());
            preparedStatement.setBigDecimal(6, entity.getFinalPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.SAVE_USER_CAR, e);
            throw new DataAccessException(ErrorMessages.SAVE_USER_CAR, e);
        }
    }

    @Override
    public void update(UserCarEntity entity) {
        final String query = "UPDATE user_car SET user_id = ?, car_id = ?, start_trip = ?, end_trip = ?, price_per_minute = ?, final_price = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setLong(2, entity.getCarId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTrip()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTrip()));
            preparedStatement.setBigDecimal(5, entity.getPriceForMinute());
            preparedStatement.setBigDecimal(6, entity.getFinalPrice());
            preparedStatement.setLong(7, entity.getId());
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
                    "INSERT INTO user_car (user_id, car_id, start_trip, end_trip, price_per_minute, final_price) VALUES (?, ?, ?, ?, ?, ?)")) {
                insertStmt.setLong(1, userId);
                insertStmt.setLong(2, carDto.getId());
                insertStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                insertStmt.setTimestamp(4, null);
                insertStmt.setBigDecimal(5, CarPriceUtil.getCarPrice(carDto));
                insertStmt.setBigDecimal(6, null);
                insertStmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            log.error(ErrorMessages.BOOK_CAR + carDto.getId(), e);
            throw new DataAccessException(ErrorMessages.BOOK_CAR + carDto.getId(), e);
        }
    }

    public List<TripDto> findActiveTripsWithCarByUser(Long userId) {
        final String query = """
                SELECT uc.id AS trip_id, c.model, c.manufacturer, uc.start_trip, uc.end_trip, uc.final_price
                FROM user_car uc
                JOIN car c ON uc.car_id = c.id
                WHERE uc.user_id = ? AND uc.end_trip IS NULL
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                List<TripDto> result = new ArrayList<>();
                while (rs.next()) {
                    TripDto dto = new TripDto();
                    dto.setId(rs.getLong("trip_id"));
                    dto.setCarModel(rs.getString("model"));
                    dto.setCarManufacturer(rs.getString("manufacturer"));
                    dto.setStartTrip(rs.getTimestamp("start_trip").toLocalDateTime());
                    Timestamp endTrip = rs.getTimestamp("end_trip");
                    if (endTrip != null) {
                        dto.setEndTrip(endTrip.toLocalDateTime());
                    }
                    dto.setFinalPrice(rs.getBigDecimal("final_price"));
                    result.add(dto);
                }
                return result;
            }

        } catch (SQLException e) {
            log.error(ErrorMessages.FETCH_ACTIVE_TRIPS + userId, e);
            throw new DataAccessException(ErrorMessages.FETCH_ACTIVE_TRIPS, e);
        }
    }

    public void finishTrip(Long tripId) {
        final String selectTripQuery = "SELECT * FROM user_car WHERE id = ? FOR UPDATE";
        final String updateTripQuery = "UPDATE user_car SET end_trip = ?, final_price = ? WHERE id = ?";
        final String updateCarStatusQuery = "UPDATE car SET status = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            long carId;
            LocalDateTime startTrip;
            BigDecimal pricePerMinute;

            try (PreparedStatement selectStmt = conn.prepareStatement(selectTripQuery)) {
                selectStmt.setLong(1, tripId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new NoSuchElementException(ErrorMessages.TRIP_NOT_FOUND + tripId);
                    }
                    carId = rs.getLong("car_id");
                    Timestamp startTimestamp = rs.getTimestamp("start_trip");
                    if (startTimestamp == null) {
                        conn.rollback();
                        throw new IllegalStateException(ErrorMessages.START_TIME_IS_NULL);
                    }
                    startTrip = startTimestamp.toLocalDateTime();
                    pricePerMinute = rs.getBigDecimal("price_per_minute");
                }
            }

            LocalDateTime endTrip = LocalDateTime.now();
            long durationMinutes = Duration.between(startTrip, endTrip).toMinutes();
            if (durationMinutes == 0) {
                durationMinutes = 1;
            }
            BigDecimal finalPrice = pricePerMinute.multiply(BigDecimal.valueOf(durationMinutes));

            try (PreparedStatement updateTripStmt = conn.prepareStatement(updateTripQuery)) {
                updateTripStmt.setTimestamp(1, Timestamp.valueOf(endTrip));
                updateTripStmt.setBigDecimal(2, finalPrice);
                updateTripStmt.setLong(3, tripId);
                updateTripStmt.executeUpdate();
            }

            try (PreparedStatement updateCarStmt = conn.prepareStatement(updateCarStatusQuery)) {
                updateCarStmt.setString(1, Status.AVAILABLE.toString());
                updateCarStmt.setLong(2, carId);
                updateCarStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            log.error(ErrorMessages.END_TRIP + tripId, e);
            throw new DataAccessException(ErrorMessages.END_TRIP + tripId, e);
        }
    }


}