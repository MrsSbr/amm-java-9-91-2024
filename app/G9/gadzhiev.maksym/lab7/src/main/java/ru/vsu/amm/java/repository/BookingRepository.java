package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entity.Booking;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingRepository implements BaseRepository<Booking> {
    private final DataSource dataSource;

    public BookingRepository() {
        dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<Booking> findById(Long id) throws SQLException {
        final String query = """
                SELECT id, check_in_date, check_out_date, status, user_id, real_estate_id
                FROM booking  WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setLong(1, id);

            try(ResultSet result = pStmt.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new Booking(
                            result.getLong("id"),
                            result.getDate("check_in_date").toLocalDate(),
                            result.getDate("check_out_date").toLocalDate(),
                            result.getString("status"),
                            result.getLong("user_id"),
                            result.getLong("real_estate_id"))
                    );
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(Booking entity) throws SQLException {
        final String query = """
                UPDATE booking
                SET check_in_date = ?, check_out_date = ?,
                status = ?, user_id = ?, real_estate_id = ?
                WHERE id = ?""";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPreparedStatement(pStmt, entity);
            pStmt.setLong(6, entity.getId());
            pStmt.execute();
        }
    }

    @Override
    public boolean save(Booking entity) throws SQLException {
        final String query = """
                INSERT INTO booking (check_in_date, check_out_date,
                status, user_id, real_estate_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        try(Connection  connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPreparedStatement(pStmt, entity);
            return pStmt.executeUpdate() == 1;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM booking WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {
            pStmt.setLong(1, id);
            pStmt.execute();
        }
    }

    private void setPreparedStatement(PreparedStatement pStmt, Booking entity) throws SQLException {
        pStmt.setDate(1, Date.valueOf(String.valueOf(entity.getCheckInDate())));
        pStmt.setDate(2, Date.valueOf(String.valueOf(entity.getCheckOutDate())));
        pStmt.setString(3, entity.getStatus());
        pStmt.setLong(4, entity.getUserId());
        pStmt.setLong(5, entity.getRealEstateId());
    }

    public List<Booking> findByUserId(Long userId) throws SQLException{
        final String query = """
                SELECT id, check_in_date, check_out_date, status, user_id, real_estate_id
                FROM booking WHERE user_id = ?
                """;
        List<Booking> bookingList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setLong(1, userId);

            try (ResultSet result = pStmt.executeQuery()) {
                while (result.next()) {
                    bookingList.add(new Booking(
                            result.getLong("id"),
                            result.getDate("check_in_date").toLocalDate(),
                            result.getDate("check_out_date").toLocalDate(),
                            result.getString("status"),
                            result.getLong("user_id"),
                            result.getLong("real_estate_id")
                    ));
                }
            }
        }
        return bookingList;
    }

    public boolean updateStatus(Long bookingId, String status) throws SQLException {
        final String query = "UPDATE booking SET status = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            pStmt.setString(1, status);
            pStmt.setLong(2, bookingId);
            return pStmt.executeUpdate() == 1;
        }
    }

    public boolean checkBusy(Long estateId, LocalDate checkIn, LocalDate checkOut) throws SQLException {
        final String query = """ 
                SELECT EXISTS (SELECT 1 FROM booking
                WHERE real_estate_id = ? AND status = 'Забронировано' AND check_in_date < ? AND check_out_date > ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {
            pStmt.setLong(1, estateId);
            pStmt.setDate(2, Date.valueOf(checkOut));
            pStmt.setDate(3, Date.valueOf(checkIn));
            try(ResultSet result = pStmt.executeQuery()) {
                if (result.next()) {
                    return result.getBoolean(1);
                }
            }
        }
        return false;
    }
}
