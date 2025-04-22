package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserCarEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCarMapper {
    public static UserCarEntity ResultSetToUserCarEntity(ResultSet rs) throws SQLException {
        return new UserCarEntity(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("car_id"),
                rs.getTimestamp("start_trip").toLocalDateTime(),
                rs.getTimestamp("end_trip").toLocalDateTime(),
                rs.getBigDecimal("price_per_minute")
        );
    }
}
