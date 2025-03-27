package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entity.Booking;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class BookingRepository implements BaseRepository<Booking> {
    private final DataSource dataSource;

    public BookingRepository() {
        dataSource = DatabaseConfig.getDataSource();
    }

    @Override
    public Optional<Booking> findById(Long id) throws SQLException {
        final String query = """
                SELECT id, checkInDate, checkOutDate, price, status, userId, realEstateId
                FROM booking  WHERE id = ?
                """;

        var connection = dataSource.getConnection();

        var pStmt = connection.prepareStatement(query);
        pStmt.setLong(1, id);
        pStmt.execute();

        var result = pStmt.getResultSet();
        if (result.next()) {
            return Optional.of(new Booking(
                    result.getLong("id"),
                    result.getDate("checkInDate").toLocalDate(),
                    result.getDate("checkOutDate").toLocalDate(),
                    result.getLong("price"),
                    result.getString("status"),
                    result.getLong("userId"),
                    result.getLong("realEstateId"))
            );
        }
        return Optional.empty();
    }

    @Override
    public void update(Booking entity) throws SQLException {
        final String query = """
                UPDATE booking
                SET checkInDate = ?, checkOutDate = ?, price = ?
                status = ?, userId = ?, realEstateId = ?
                WHERE id = ?""";

        var connection = dataSource.getConnection();

        var pStmt = connection.prepareStatement(query);
        setPreparedStatement(pStmt, entity);
        pStmt.setLong(7, entity.getId());
        pStmt.execute();
    }

    @Override
    public void save(Booking entity) throws SQLException {
        final String query = """
                INSERT INTO booking (checkInDate, checkOutDate, price,
                status, userId, realEstateId)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        var connection = dataSource.getConnection();
        var pStmt = connection.prepareStatement(query);
        setPreparedStatement(pStmt, entity);
        pStmt.execute();
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM booking WHERE id = ?";
        var connection = dataSource.getConnection();

        var pStmt = connection.prepareStatement(query);
        pStmt.setLong(1, id);
        pStmt.execute();
    }

    private void setPreparedStatement(PreparedStatement pStmt, Booking entity) throws SQLException {
        pStmt.setDate(1, Date.valueOf(String.valueOf(entity.getCheckInDate())));
        pStmt.setDate(2, Date.valueOf(String.valueOf(entity.getCheckOutDate())));
        pStmt.setLong(3, entity.getPrice());
        pStmt.setString(4, entity.getStatus());
        pStmt.setLong(5, entity.getUserId());
        pStmt.setLong(6, entity.getRealEstateId());
    }
}
