package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.TripEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

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

        preparedStatement.setDouble(5, roundCoordinate(entity.getStartLatitude()));
        preparedStatement.setDouble(6, roundCoordinate(entity.getStartLongitude()));

        if (entity.getEndLatitude()!=null) {
            preparedStatement.setDouble(7, roundCoordinate(entity.getEndLatitude()));
        } else {
            preparedStatement.setNull(7, Types.DOUBLE);
        }

        if (entity.getEndLongitude()!=null) {
            preparedStatement.setDouble(8, roundCoordinate(entity.getEndLongitude()));
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

        preparedStatement.setDouble(6, roundCoordinate(entity.getStartLatitude()));
        preparedStatement.setDouble(7, roundCoordinate(entity.getStartLongitude()));

        if (entity.getEndLatitude()!=null) {
            preparedStatement.setDouble(8, roundCoordinate(entity.getEndLatitude()));
        } else {
            preparedStatement.setNull(8, Types.DOUBLE);
        }

        if (entity.getEndLongitude()!=null) {
            preparedStatement.setDouble(9, roundCoordinate(entity.getEndLongitude()));
        } else {
            preparedStatement.setNull(9, Types.DOUBLE);
        }
        preparedStatement.setLong(10, entity.getId());
    }

    public static double roundCoordinate(double coordinate) {
        if (coordinate < -180 || coordinate > 180) {
            throw new IllegalArgumentException("Координата вне допустимого диапазона (-180 до 180)");
        }
        return BigDecimal.valueOf(coordinate).setScale(6, RoundingMode.HALF_UP).doubleValue();
    }
}
