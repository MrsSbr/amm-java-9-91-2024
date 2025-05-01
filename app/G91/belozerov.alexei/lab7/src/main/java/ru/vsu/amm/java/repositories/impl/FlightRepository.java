package ru.vsu.amm.java.repositories.impl;

import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.FlightEntity;
import ru.vsu.amm.java.repositories.CrudRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightRepository implements CrudRepository<FlightEntity> {

    private final DataSource ds;

    public FlightRepository() {
        ds = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<FlightEntity> findById(Long id) throws SQLException {
        final String query = "SELECT flight_id, origin, destination, departure_time, " +
            "arrival_time, airplane_model, capacity, price FROM flight WHERE flight_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createFlight(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FlightEntity> findAll() throws SQLException {
        final String query = "SELECT flight_id, origin, destination, departure_time, " +
            "arrival_time, airplane_model, capacity, price FROM flight";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<FlightEntity> flights = new ArrayList<>();
            while (rs.next()) {
                flights.add(createFlight(rs));
            }
            return flights;
        }
    }

    @Override
    public void save(FlightEntity entity) throws SQLException {
        final String query = "INSERT INTO flight (flight_id, origin, destination, departure_time, " +
            "arrival_time, airplane_model, capacity, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, entity.getFlightId());
            ps.setString(2, entity.getOrigin());
            ps.setString(3, entity.getDestination());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getDepartureTime()));
            ps.setTimestamp(5, Timestamp.valueOf(entity.getArrivalTime()));
            ps.setString(6, entity.getAirplaneModel());
            ps.setShort(7, entity.getCapacity());
            ps.setBigDecimal(8, entity.getPrice());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(FlightEntity entity) throws SQLException {
        final String query = "UPDATE flight SET origin = ?, destination = ?, departure_time = ?, " +
            "arrival_date = ?, airplane_model = ?, capacity = ?, price = ? WHERE flight_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, entity.getOrigin());
            ps.setString(2, entity.getDestination());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
            ps.setTimestamp(4, Timestamp.valueOf(entity.getArrivalTime()));
            ps.setString(5, entity.getAirplaneModel());
            ps.setShort(6, entity.getCapacity());
            ps.setBigDecimal(7, entity.getPrice());
            ps.setLong(8, entity.getFlightId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM flight WHERE flight_id = ?";
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private FlightEntity createFlight(ResultSet rs) throws SQLException {
        return new FlightEntity(
            rs.getLong("flight_id"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getTimestamp("departure_time").toLocalDateTime(),
            rs.getTimestamp("arrival_time").toLocalDateTime(),
            rs.getString("airplane_model"),
            rs.getShort("capacity"),
            rs.getBigDecimal("price")
        );
    }
}
