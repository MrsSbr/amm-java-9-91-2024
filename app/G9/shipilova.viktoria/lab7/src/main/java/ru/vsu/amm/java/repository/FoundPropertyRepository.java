package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enams.ReturnStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoundPropertyRepository implements CrudRepository<FoundProperty> {
    private static final Logger logger = Logger.getLogger(FoundPropertyRepository.class.getName());

    @Override
    public FoundProperty getById(long id) {
        FoundProperty foundProperty = null;
        final String sql = "SELECT FoundPropertyID, PropertyTypeID, DateOfFinding, TimeOfFinding, ReturnStatus, PlaceOfFinding, Description, UserID FROM FoundProperties WHERE FoundPropertyID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                foundProperty = new FoundProperty();
                foundProperty.setId(rs.getLong("FoundPropertyID"));

                long propertyTypeId = rs.getLong("PropertyTypeID");
                PropertyType propertyType = new PropertyType();
                propertyType.setId(propertyTypeId);
                foundProperty.setPropertyType(propertyType);

                foundProperty.setDateOfFinding(rs.getDate("DateOfFinding").toLocalDate());
                foundProperty.setTimeOfFinding(rs.getTime("TimeOfFinding").toLocalTime());
                foundProperty.setReturnStatus(ReturnStatus.valueOf(rs.getString("ReturnStatus")));
                foundProperty.setPlaceOfFinding(rs.getString("PlaceOfFinding"));
                foundProperty.setDescription(rs.getString("Description"));

                long userId = rs.getLong("UserID");
                User user = new User();
                user.setId(userId);
                foundProperty.setUser(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return foundProperty;
    }

    @Override
    public List<FoundProperty> getAll() {
        List<FoundProperty> foundProperties = new ArrayList<>();
        final String sql = "SELECT FoundPropertyID, PropertyTypeID, DateOfFinding, TimeOfFinding, ReturnStatus, PlaceOfFinding, Description, UserID FROM FoundProperties";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FoundProperty foundProperty = new FoundProperty();
                foundProperty.setId(rs.getLong("FoundPropertyID"));

                long propertyTypeId = rs.getLong("PropertyTypeID");
                PropertyType propertyType = new PropertyType();
                propertyType.setId(propertyTypeId);
                foundProperty.setPropertyType(propertyType);

                foundProperty.setDateOfFinding(rs.getDate("DateOfFinding").toLocalDate());
                foundProperty.setTimeOfFinding(rs.getTime("TimeOfFinding").toLocalTime());
                foundProperty.setReturnStatus(ReturnStatus.valueOf(rs.getString("ReturnStatus")));
                foundProperty.setPlaceOfFinding(rs.getString("PlaceOfFinding"));
                foundProperty.setDescription(rs.getString("Description"));

                long userId = rs.getLong("UserID");
                User user = new User();
                user.setId(userId);
                foundProperty.setUser(user);

                foundProperties.add(foundProperty);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return foundProperties;
    }

    @Override
    public void save(FoundProperty foundProperty) {
        final String sql = "INSERT INTO FoundProperties (PropertyTypeID, DateOfFinding, TimeOfFinding, ReturnStatus, PlaceOfFinding, Description, UserID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, foundProperty.getPropertyType().getId());
            ps.setDate(2, java.sql.Date.valueOf(foundProperty.getDateOfFinding()));
            ps.setTime(3, java.sql.Time.valueOf(foundProperty.getTimeOfFinding()));
            ps.setString(4, foundProperty.getReturnStatus().name());
            ps.setString(5, foundProperty.getPlaceOfFinding());
            ps.setString(6, foundProperty.getDescription());
            ps.setLong(7, foundProperty.getUser().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void update(FoundProperty foundProperty) {
        final String sql = "UPDATE FoundProperties SET PropertyTypeID = ?, DateOfFinding = ?, TimeOfFinding = ?, ReturnStatus = ?, PlaceOfFinding = ?, Description = ?, UserID = ? WHERE FoundPropertyID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, foundProperty.getPropertyType().getId());
            ps.setDate(2, java.sql.Date.valueOf(foundProperty.getDateOfFinding()));
            ps.setTime(3, java.sql.Time.valueOf(foundProperty.getTimeOfFinding()));
            ps.setString(4, foundProperty.getReturnStatus().name());
            ps.setString(5, foundProperty.getPlaceOfFinding());
            ps.setString(6, foundProperty.getDescription());
            ps.setLong(7, foundProperty.getUser().getId());
            ps.setLong(8, foundProperty.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        final String sql = "DELETE FROM FoundProperties WHERE FoundPropertyID = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
