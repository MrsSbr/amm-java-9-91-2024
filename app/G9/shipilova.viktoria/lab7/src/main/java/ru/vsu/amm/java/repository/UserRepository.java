package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.connection.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<User> {

    @Override
    public User getById(long id) {
        User user = null;
        final String sql = "SELECT UserID, Password, PhoneNumber, Email, Login FROM users WHERE UserID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("UserID"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setLogin(rs.getString("Login"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //log
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT UserID, Password, PhoneNumber, Email, Login FROM Users";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("UserID"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setLogin(rs.getString("Login"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //log
        }

        return users;

    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO Users (Password, PhoneNumber, Email, Login) VALUES (?, ?, ?, ?)";


        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getLogin());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); //log
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE Users SET Password = ?, PhoneNumber = ?, Email = ?, Login = ? WHERE UserID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getLogin());
            ps.setLong(5, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); //log
        }

    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM Users WHERE UserID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e); //log
        }

    }
}
