package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserEntity ResultSetToUserEntity(ResultSet rs) throws SQLException {
        return new UserEntity(rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"));
    }
}
