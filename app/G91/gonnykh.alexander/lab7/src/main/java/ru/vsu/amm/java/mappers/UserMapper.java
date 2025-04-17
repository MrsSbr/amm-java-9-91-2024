package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserEntity resultSetToUserEntity(ResultSet rs) throws SQLException {
        return new UserEntity(rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                Role.valueOf(rs.getString("role")));
    }
}
