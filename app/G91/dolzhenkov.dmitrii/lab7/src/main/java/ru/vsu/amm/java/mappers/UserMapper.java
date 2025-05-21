package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserEntity ResultSetToUserEntity(ResultSet rs) throws SQLException {
        return new UserEntity(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("password"),
                UserRole.valueOf(rs.getString("role")));
    }
}
