package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserCarEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCarMapper {
    public static UserCarEntity ResultSetToUserCarEntity(ResultSet rs) throws SQLException {
        return new UserCarEntity(rs.getLong("id"),
                rs.getLong("userId"),
                rs.getLong("carId"),
                rs.getTimestamp("startTrip").toLocalDateTime(),
                rs.getInt("duration"),
                rs.getBigDecimal("priceForMinute")
        );
    }
}
