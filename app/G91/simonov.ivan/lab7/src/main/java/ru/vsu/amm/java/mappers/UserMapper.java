package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

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
}
