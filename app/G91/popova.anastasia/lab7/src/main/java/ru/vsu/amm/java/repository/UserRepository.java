package ru.vsu.amm.java.repository;

import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.DB_config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class UserRepository implements CRUDRepository<User> {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserRepository.class);

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID((UUID)rs.getObject("UserID"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setName(rs.getString("Name"));
        return user;
    }

    @Override
    public User getByID(UUID userID) {
        final String sql = "SELECT \"UserID\", \"Password\", \"Email\", \"Name\" FROM users WHERE \"UserID\" = ?";
        log.debug("Fetching user with ID: {}", userID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return mapResultSetToUser(rs);
            }
            log.info("Successfully fetched user: {}", userID);
        } catch(SQLException e) {
            log.error("Failed to fetch user: {}", userID + e.getMessage());
        }
        return null;
    }

    public User getByEmail(String email) {
        final String sql = "SELECT \"UserID\", \"Password\", \"Email\", \"Name\" FROM users WHERE \"Email\" = ?";
        log.debug("Fetching user with email {}", email);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
               return mapResultSetToUser(rs);
            }
            log.info("Successfully fetched user");
        } catch (SQLException e) {
            log.error("Failed to fetch user by email: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT \"UserID\", \"Password\", \"Email\", \"Name\" FROM users";
        log.debug("Fetching all users");

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            log.info("Successfully fetched {} users", users.size());
        } catch (SQLException e) {
            log.error("Failed to fetch all users" + e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User user) {
        final String sql = "INSERT INTO users (\"UserID\", \"Password\", \"Email\", \"Name\") VALUES (?, ?, ?, ?)";
        log.debug("Saving user: {}", user.getUserID());

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, user.getUserID());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to save user: {}", user.getUserID() + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        final String sql = "UPDATE users SET \"Password\" = ?, \"Email\" = ?, \"Name\" = ? WHERE \"UserID\" = ?";
        log.debug("Updating user: {}", user.getUserID());
        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getUserID());
            ps.executeUpdate();
            log.info("User updated successfully: {}", user.getUserID());
        } catch (SQLException e) {
            log.error("Failed to update user: {}", user.getUserID() + e.getMessage());
        }
    }

    @Override
    public void delete(UUID userID) {
       final String sql = "DELETE FROM users WHERE \"UserID\" = ?";
        log.debug("Deleting user: {}", userID);

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, userID);
            ps.executeUpdate();
            log.info("User deleted successfully: {}", userID);
        } catch (SQLException e) {
            log.error("Failed to delete user: {}", userID + e.getMessage());
        }
    }

}
