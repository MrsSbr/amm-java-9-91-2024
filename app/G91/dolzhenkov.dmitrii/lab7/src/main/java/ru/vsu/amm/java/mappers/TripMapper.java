package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.TripEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TripMapper {
    public static TripEntity resultSetToUserCarEntity(ResultSet rs) throws SQLException {
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

    public static void setPreparedStatement (PreparedStatement preparedStatement, TripEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getUserId());
        preparedStatement.setLong(2, entity.getScooterId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
        preparedStatement.setDouble(5, entity.getStartLatitude());
        preparedStatement.setDouble(6, entity.getStartLongitude());
        preparedStatement.setDouble(7, entity.getEndLatitude());
        preparedStatement.setDouble(8, entity.getEndLongitude());
    }
}
