package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User mapRowToObject(ResultSet rs) throws SQLException {

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

    public static void mapObjectToRow(User entity, PreparedStatement stmt) throws SQLException {

        stmt.setString(1, entity.getLastName());
        stmt.setString(2, entity.getFirstName());
        stmt.setString(3, entity.getPatronymic());
        stmt.setString(4, entity.getLogin());
        stmt.setString(5, entity.getPassword());
        stmt.setString(6, entity.getRole().name());
    }
}
