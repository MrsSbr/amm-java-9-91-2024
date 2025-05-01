package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.TripEntity;

import java.sql.*;

public class TripMapper {
    public static TripEntity resultSetToUserCarEntity(ResultSet rs) throws SQLException {
        Timestamp endTimestamp = rs.getTimestamp("end_time");
        Double endLatitude = rs.getObject("end_latitude") != null ? rs.getDouble("end_latitude") : null;
        Double endLongitude = rs.getObject("end_longitude") != null ? rs.getDouble("end_longitude") : null;

        return new TripEntity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("scooter_id"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                endTimestamp != null ? endTimestamp.toLocalDateTime() : null,
                rs.getDouble("start_latitude"),
                rs.getDouble("start_longitude"),
                endLatitude,
                endLongitude
        );
    }

    public static void setPreparedStatement (PreparedStatement preparedStatement, TripEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getUserId());
        preparedStatement.setLong(2, entity.getScooterId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));

        if (entity.getEndTime() != null) {
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
        } else {
            preparedStatement.setNull(4, java.sql.Types.TIMESTAMP);
        }

        preparedStatement.setDouble(5, entity.getStartLatitude());
        preparedStatement.setDouble(6, entity.getStartLongitude());

        if (entity.getEndLatitude()!=null) {
            preparedStatement.setDouble(7, entity.getEndLatitude());
        } else {
            preparedStatement.setNull(7, Types.DOUBLE);
        }

        if (entity.getEndLongitude()!=null) {
            preparedStatement.setDouble(8, entity.getEndLongitude());
        } else {
            preparedStatement.setNull(8, Types.DOUBLE);
        }
    }

    public static void setUpdatePreparedStatement (PreparedStatement preparedStatement, TripEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.setLong(2, entity.getUserId());
        preparedStatement.setLong(3, entity.getScooterId());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getStartTime()));

        if (entity.getEndTime() != null) {
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getEndTime()));
        } else {
            preparedStatement.setNull(5, java.sql.Types.TIMESTAMP);
        }

        preparedStatement.setDouble(6, entity.getStartLatitude());
        preparedStatement.setDouble(7, entity.getStartLongitude());

        if (entity.getEndLatitude()!=null) {
            preparedStatement.setDouble(8, entity.getEndLatitude());
        } else {
            preparedStatement.setNull(8, Types.DOUBLE);
        }

        if (entity.getEndLongitude()!=null) {
            preparedStatement.setDouble(9, entity.getEndLongitude());
        } else {
            preparedStatement.setNull(9, Types.DOUBLE);
        }
        preparedStatement.setLong(10, entity.getId());
    }
}
