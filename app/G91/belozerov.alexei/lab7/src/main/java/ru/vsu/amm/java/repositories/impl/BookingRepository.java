package ru.vsu.amm.java.repositories.impl;

import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.repositories.CrudRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingRepository implements CrudRepository<BookingEntity> {

    private final DataSource ds;

    public BookingRepository() {
        ds = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<BookingEntity> findById(Long id) throws SQLException {
        final String query = "SELECT booking_id, client_id, flight_id, seat_number, ticket_number " +
            "FROM booking WHERE booking_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createBooking(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BookingEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM booking";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<BookingEntity> booking = new ArrayList<>();
            while (rs.next()) {
                booking.add(createBooking(rs));
            }
            return booking;
        }
    }

    @Override
    public void save(BookingEntity entity) throws SQLException {
        final String query = "INSERT INTO booking (booking_id, client_id, flight_id, seat_number, ticket_number) " +
            "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, entity.getBookingId());
            ps.setLong(2, entity.getClientId());
            ps.setLong(3, entity.getFlightId());
            ps.setString(4, entity.getSeatNumber());
            ps.setString(5, entity.getTicketNumber());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(BookingEntity entity) throws SQLException {
        final String query = "UPDATE booking SET client_id = ?, flight_id = ?, seat_number = ?, " +
            "ticket_number = ? WHERE booking_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, entity.getClientId());
            ps.setLong(2, entity.getFlightId());
            ps.setString(3, entity.getSeatNumber());
            ps.setString(4, entity.getTicketNumber());
            ps.setLong(5, entity.getBookingId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM booking WHERE booking_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private BookingEntity createBooking(ResultSet rs) throws SQLException {
        return new BookingEntity(
            rs.getLong("booking_id"),
            rs.getLong("client_id"),
            rs.getLong("flight_id"),
            rs.getString("seat_number"),
            rs.getString("ticket_number")
        );
    }
}
