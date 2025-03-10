package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entities.NextDestination;
import ru.vsu.amm.java.connection.DatabaseConnection;
import ru.vsu.amm.java.enams.NextDestinationName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NextDestinationRepository implements CrudRepository<NextDestination> {

    @Override
    public NextDestination getById(long id) {
        NextDestination nextDestination = null;
        final String sql = "SELECT NextDestinationID, NextDestinationName, Description FROM NextDestination WHERE NextDestinationID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nextDestination = new NextDestination();
                nextDestination.setId(rs.getLong("NextDestinationID"));

                nextDestination.setNextDestinationName(NextDestinationName.valueOf(rs.getString("NextDestinationName")));
                nextDestination.setDescription(rs.getString("Description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }

        return nextDestination;
    }

    @Override
    public List<NextDestination> getAll() {
        List<NextDestination> nextDestinations = new ArrayList<>();
        final String sql = "SELECT NextDestinationID, NextDestinationName, Description FROM NextDestination";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NextDestination nextDestination = new NextDestination();
                nextDestination.setId(rs.getLong("NextDestinationID"));

                nextDestination.setNextDestinationName(NextDestinationName.valueOf(rs.getString("NextDestinationName")));
                nextDestination.setDescription(rs.getString("Description"));

                nextDestinations.add(nextDestination);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }

        return nextDestinations;
    }

    @Override
    public void save(NextDestination nextDestination) {
        final String sql = "INSERT INTO NextDestination (NextDestinationName, Description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nextDestination.getNextDestinationName().name());
            ps.setString(2, nextDestination.getDescription());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }

    @Override
    public void update(NextDestination nextDestination) {
        final String sql = "UPDATE NextDestination SET NextDestinationName = ?, Description = ? WHERE NextDestinationID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nextDestination.getNextDestinationName().name());
            ps.setString(2, nextDestination.getDescription());
            ps.setLong(3, nextDestination.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }

    @Override
    public void delete(long id) {
        final String sql = "DELETE FROM NextDestination WHERE NextDestinationID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }
}
