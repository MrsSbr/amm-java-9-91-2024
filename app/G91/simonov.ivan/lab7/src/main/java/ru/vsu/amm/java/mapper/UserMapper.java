package ru.vsu.amm.java.mapper;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.requests.RegisterRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserMapper implements EntityMapper<User> {

    public UserMapper() {}

    public PreparedStatement mapAuthorisation(Connection connection,
                                              String login,
                                              String password,
                                              String sql) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, login);
        stmt.setString(2, password);

        return stmt;
    }

    public User mapRequestToObject(RegisterRequest request) {

        User user = new User();

        user.setLastName(request.lastName());
        user.setFirstName(request.firstName());
        user.setPatronymic(request.patronymic());
        user.setLogin(request.login());
        user.setPassword(request.password());
        user.setRole(request.role());

        return user;
    }

    @Override
    public User mapRowToObject(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("Id_user"));
        user.setLastName(rs.getString("LastName"));
        user.setFirstName(rs.getString("FirstName"));
        user.setPatronymic(rs.getString("Patronymic"));
        user.setLogin(rs.getString("Login"));
        user.setPassword(rs.getString("Password"));
        user.setRole(Role.valueOf(rs.getString("Role")));

        return user;
    }

    @Override
    public PreparedStatement mapObjectToRow(User entity,
                                            Connection connection,
                                            String sql) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, entity.getLastName());
        stmt.setString(2, entity.getFirstName());
        stmt.setString(3, entity.getPatronymic());
        stmt.setString(4, entity.getLogin());
        stmt.setString(5, entity.getPassword());
        stmt.setString(6, entity.getRole().name());

        return stmt;
    }
}
