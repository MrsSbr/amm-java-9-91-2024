package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements CrudRepository<User> {
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

    @Override
    public User getById(long id) {
        User user = null;
        final String sql = "SELECT UserId, Password, PhoneNumber, Email, Login, Username FROM Users WHERE UserId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getLong("UserId"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setLogin(rs.getString("Login"));
                user.setUsername(rs.getString("Username"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT UserId, Password, PhoneNumber, Email, Login, Username FROM Users";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("UserId"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setLogin(rs.getString("Login"));
                user.setUsername(rs.getString("Username"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO Users (Password, PhoneNumber, Email, Login, Username) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getUsername());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE Users SET Password = ?, PhoneNumber = ?, Email = ?, Username = ? WHERE UserId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setLong(5, user.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM Users WHERE UserId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = null;
        final String sql = "SELECT UserId, Password, PhoneNumber, Email, Login, Username FROM Users WHERE Login = ? AND Password = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getLong("UserId"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setLogin(rs.getString("Login"));
                user.setUsername(rs.getString("Username"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return user;
    }
}