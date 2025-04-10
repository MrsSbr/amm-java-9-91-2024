package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Enums.Role;

import java.sql.*;
import java.time.LocalDate;

public class UserMapper implements Mapper<User> {
    public UserMapper() {
    }

    @Override
    public User mapRowToObject(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserId(rs.getLong("UserId"));
        user.setSurname(rs.getString("Surname"));
        user.setName(rs.getString("Name"));
        user.setPatronymicname(rs.getString("Patronymicname"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setBirthday(rs.getDate("Birthday").toLocalDate());
        user.setRole(Role.valueOf(rs.getString("Role")));

        return user;
    }

    @Override
    public PreparedStatement mapObjectToRow(User user,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getSurname());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPatronymicname());
        preparedStatement.setString(4, user.getPhoneNumber());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setDate(7, Date.valueOf(user.getBirthday()));
        preparedStatement.setString(8, user.getRole().toString());

        return preparedStatement;
    }
}
