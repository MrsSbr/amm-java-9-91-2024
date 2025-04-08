package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.RegisterRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
    public UserMapper() {
    }

    @Override
    public User mapRowToObject(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getInt("Id_user"));
        user.setEmail(resultSet.getString("Email"));
        user.setPassword(resultSet.getString("Password"));
        user.setLastName(resultSet.getString("LastName"));
        user.setFirstName(resultSet.getString("FirstName"));
        user.setPatronymic(resultSet.getString("PatronymicName"));
        user.setPhoneNumber(resultSet.getString("PhoneNumber"));

        return user;
    }

    @Override
    public PreparedStatement mapObjectToRow(User user,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getLastName());
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getPatronymic());
        ps.setString(6, user.getPhoneNumber());

        return ps;
    }

    public User mapRequestToObject(RegisterRequest request) {

        User user = new User();

        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setLastName(request.lastName());
        user.setFirstName(request.firstName());
        user.setPatronymic(request.patronymicName());
        user.setPhoneNumber(request.phoneNumber());

        return user;
    }
}
