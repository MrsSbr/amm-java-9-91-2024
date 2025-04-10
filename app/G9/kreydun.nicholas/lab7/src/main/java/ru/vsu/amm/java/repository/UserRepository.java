package ru.vsu.amm.java.repository;

import org.jetbrains.annotations.Nullable;
import ru.vsu.amm.java.dbManage.DbConnection;
import ru.vsu.amm.java.entities.User;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.vsu.amm.java.services.Logg.logger;

public class UserRepository implements Repository<User> {

    @Override
    public User getById(UUID id) {
        User user = null;
        String sql = "SELECT \"ID\", Username, Email, \"Password\", Created_at FROM Users WHERE \"ID\" = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(UUID.fromString(rs.getString("ID")));
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                }
            }
        } catch (SQLException e) {
            logger.warning("Error getting user by ID: " + e.getMessage());
        }
        return user;
    }

    @Nullable
    public User getByEmail(String email) {
        String sql = "SELECT \"ID\", Username, Email, \"Password\", Created_at FROM Users WHERE Email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);  // Передаем email в SQL-запрос
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(UUID.fromString(rs.getString("ID")));  // ID остается UUID
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error getting user by email: " + e.getMessage());
        }
        return null;  // Если пользователя нет, возвращаем null
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT \"ID\", Username, Email, \"Password\", Created_at FROM Users";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("ID")));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setCreatedAt(rs.getTime("Created_at").toLocalTime());;
                users.add(user);
            }

        } catch (SQLException e) {
            logger.warning("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public UUID create(User user) {
        UUID newId = UUID.randomUUID();
        String sql = "INSERT INTO Users (\"ID\", Username, Email, \"Password\") VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, newId);
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());

            int affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0) ? newId : null;

        } catch (SQLException e) {
            logger.warning("Error creating user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE Users SET Username = ?, Email = ?, \"Password\" = ? WHERE \"ID\" = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setObject(4, user.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.warning("Error updating user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        String sql = "DELETE FROM Users WHERE \"ID\" = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warning("Error deleting user: " + e.getMessage());
            return false;
        }
    }


}

