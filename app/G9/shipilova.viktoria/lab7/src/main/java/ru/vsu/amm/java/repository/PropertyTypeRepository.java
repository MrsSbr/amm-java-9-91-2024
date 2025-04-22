package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.NextDestination;
import ru.vsu.amm.java.enams.PropertyTypeName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyTypeRepository implements CrudRepository<PropertyType> {
    private static final Logger logger = Logger.getLogger(PropertyTypeRepository.class.getName());

    @Override
    public PropertyType getById(long id) {
        PropertyType propertyType = null;
        final String sql = "SELECT PropertyTypeID, PropertyTypeName, NextDestinationID, StorageDays, StorageCost FROM PropertyType WHERE PropertyTypeID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                propertyType = new PropertyType();
                propertyType.setId(rs.getLong("PropertyTypeID"));
                propertyType.setPropertyTypeName(PropertyTypeName.valueOf(rs.getString("PropertyTypeName")));

                long nextDestinationId = rs.getLong("NextDestinationID");
                NextDestination nextDestination = new NextDestination();
                nextDestination.setId(nextDestinationId);
                propertyType.setNextDestination(nextDestination);

                propertyType.setStorageDays(rs.getInt("StorageDays"));
                propertyType.setStorageCost(rs.getInt("StorageCost"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return propertyType;
    }

    @Override
    public List<PropertyType> getAll() {
        List<PropertyType> propertyTypes = new ArrayList<>();
        final String sql = "SELECT PropertyTypeID, PropertyTypeName, NextDestinationID, StorageDays, StorageCost FROM PropertyType";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyType propertyType = new PropertyType();
                propertyType.setId(rs.getLong("PropertyTypeID"));
                propertyType.setPropertyTypeName(PropertyTypeName.valueOf(rs.getString("PropertyTypeName")));

                long nextDestinationId = rs.getLong("NextDestinationID");
                NextDestination nextDestination = new NextDestination();
                nextDestination.setId(nextDestinationId);
                propertyType.setNextDestination(nextDestination);

                propertyType.setStorageDays(rs.getInt("StorageDays"));
                propertyType.setStorageCost(rs.getInt("StorageCost"));
                propertyTypes.add(propertyType);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return propertyTypes;
    }

    @Override
    public Long save(PropertyType propertyType) {
        final String sql = "INSERT INTO PropertyType (PropertyTypeName, NextDestinationID, StorageDays, StorageCost) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, propertyType.getPropertyTypeName().toString());
            ps.setLong(2, propertyType.getNextDestination().getId());
            ps.setInt(3, propertyType.getStorageDays());
            ps.setInt(4, propertyType.getStorageCost());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return propertyType.getId();
    }

    @Override
    public void update(PropertyType propertyType) {
        final String sql = "UPDATE PropertyType SET PropertyTypeName = ?, NextDestinationID = ?, StorageDays = ?, StorageCost = ? WHERE PropertyTypeID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, propertyType.getPropertyTypeName().toString());
            ps.setLong(2, propertyType.getNextDestination().getId());
            ps.setInt(3, propertyType.getStorageDays());
            ps.setInt(4, propertyType.getStorageCost());
            ps.setLong(5, propertyType.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        final String sql = "DELETE FROM PropertyType WHERE PropertyTypeID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public PropertyType getByName(PropertyTypeName propertyTypeName) {
        PropertyType propertyType = null;
        final String sql = "SELECT PropertyTypeID, PropertyTypeName, NextDestinationID, StorageDays, StorageCost FROM PropertyType WHERE PropertyTypeName = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, propertyTypeName.name());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                propertyType = new PropertyType();
                propertyType.setId(rs.getLong("PropertyTypeID"));
                propertyType.setPropertyTypeName(PropertyTypeName.valueOf(rs.getString("PropertyTypeName")));

                long nextDestinationId = rs.getLong("NextDestinationID");
                NextDestination nextDestination = new NextDestination();
                nextDestination.setId(nextDestinationId);
                propertyType.setNextDestination(nextDestination);

                propertyType.setStorageDays(rs.getInt("StorageDays"));
                propertyType.setStorageCost(rs.getInt("StorageCost"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return propertyType;
    }

}
