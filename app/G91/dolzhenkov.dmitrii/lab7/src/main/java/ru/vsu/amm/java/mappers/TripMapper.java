package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.TripEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripMapper {
    public static TripEntity ResultSetToUserCarEntity(ResultSet rs) throws SQLException {
        return new TripEntity(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("scooter_id"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getTimestamp("end_time").toLocalDateTime(),
                rs.getDouble("start_latitude"),
                rs.getDouble("start_longitude"),
                rs.getDouble("end_latitude"),
                rs.getDouble("end_longitude")
        );
    }
}
