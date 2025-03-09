package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.NextDestination;
import ru.vsu.amm.java.connection.dbConnection;
import ru.vsu.amm.java.enams.PropertyTypeName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyTypeRepository implements CrudRepository<PropertyType> {

    private final NextDestinationRepository nextDestinationRepository;

    public PropertyTypeRepository(NextDestinationRepository nextDestinationRepository) {
        this.nextDestinationRepository = nextDestinationRepository;
    }

    @Override
    public PropertyType getById(long id) {
        PropertyType propertyType = null;
        final String sql = "SELECT PropertyTypeID, PropertyTypeName, NextDestinationID, StorageDays, StorageCost FROM PropertyType WHERE PropertyTypeID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                propertyType = new PropertyType();
                propertyType.setId(rs.getLong("PropertyTypeID"));
                propertyType.setPropertyTypeName(PropertyTypeName.valueOf(rs.getString("PropertyTypeName")));

                long nextDestinationId = rs.getLong("NextDestinationID");
                NextDestination nextDestination = nextDestinationRepository.getById(nextDestinationId);
                propertyType.setNextDestination(nextDestination);

                propertyType.setStorageDays(rs.getInt("StorageDays"));
                propertyType.setStorageCost(rs.getInt("StorageCost"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }

        return propertyType;
    }

    @Override
    public List<PropertyType> getAll() {
        List<PropertyType> propertyTypes = new ArrayList<>();
        final String sql = "SELECT PropertyTypeID, PropertyTypeName, NextDestinationID, StorageDays, StorageCost FROM PropertyType";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyType propertyType = new PropertyType();
                propertyType.setId(rs.getLong("PropertyTypeID"));
                propertyType.setPropertyTypeName(PropertyTypeName.valueOf(rs.getString("PropertyTypeName")));

                long nextDestinationId = rs.getLong("NextDestinationID");
                NextDestination nextDestination = nextDestinationRepository.getById(nextDestinationId);
                propertyType.setNextDestination(nextDestination);

                propertyType.setStorageDays(rs.getInt("StorageDays"));
                propertyType.setStorageCost(rs.getInt("StorageCost"));
                propertyTypes.add(propertyType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }

        return propertyTypes;
    }

    @Override
    public void save(PropertyType propertyType) {
        final String sql = "INSERT INTO PropertyType (PropertyTypeName, NextDestinationID, StorageDays, StorageCost) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, propertyType.getPropertyTypeName().toString());
            ps.setLong(2, propertyType.getNextDestination().getId());
            ps.setInt(3, propertyType.getStorageDays());
            ps.setInt(4, propertyType.getStorageCost());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }

    @Override
    public void update(PropertyType propertyType) {
        final String sql = "UPDATE PropertyType SET PropertyTypeName = ?, NextDestinationID = ?, StorageDays = ?, StorageCost = ? WHERE PropertyTypeID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, propertyType.getPropertyTypeName().toString());
            ps.setLong(2, propertyType.getNextDestination().getId());
            ps.setInt(3, propertyType.getStorageDays());
            ps.setInt(4, propertyType.getStorageCost());
            ps.setLong(5, propertyType.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }

    @Override
    public void delete(long id) {
        final String sql = "DELETE FROM PropertyType WHERE PropertyTypeID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); // log
        }
    }
}
