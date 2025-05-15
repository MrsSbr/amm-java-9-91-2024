package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.DB_config.DatabaseConnection;

import javax.xml.crypto.Data;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class UserRepository implements CRUDRepository<User> {
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

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

        final String sql = "SELECT UserID, Password, Email, Name FROM users WHERE UserID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, userID);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch(SQLException e) {
            logger.severe("error while trying to get user: " + e.getMessage());
        }
        return null;
    }

    public User getByEmail(String email) {
        final String sql = "SELECT UserID, Password, Email, Name FROM users WHERE Email = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
               return mapResultSetToUser(rs);
            }


        } catch (SQLException e) {
            logger.severe("error while trying to get user by email: " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT UserID, Password, Email, Name FROM users";

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get all users: " + e.getMessage());

        }

        return users;
    }

    @Override
    public void save(User user) {
        final String sql = "INSERT INTO users (UserID, Password, Email, Name) VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, user.getUserID());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to save user: " + e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        final String sql = "UPDATE users SET Password = ?, Email = ?, Name = ? WHERE UserID = ?";

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getUserID());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to update user: " + e.getMessage());
        }
    }

    @Override
    public void delete(UUID userID) {
       final String sql = "DELETE FROM users WHERE UserID = ?";

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, userID);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to delete user: " + e.getMessage());
        }

    }

}
