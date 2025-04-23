package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.UserCarEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCarMapper {
    public static UserCarEntity resultSetToUserCarEntity(ResultSet rs) throws SQLException {
        return new UserCarEntity(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("car_id"),
                rs.getTimestamp("start_trip").toLocalDateTime(),
                rs.getTimestamp("end_trip").toLocalDateTime(),
                rs.getBigDecimal("price_per_minute"),
                rs.getBigDecimal("final_price")
        );
    }

/*    public static TripDto userCarEntityToTripDto(UserCarEntity userCarEntity) {
     return new TripDto(userCarEntity.getId(),
             userCarEntity,
             userCarEntity.getCarId(),
             userCarEntity.getStartTrip(),
             userCarEntity.getEndTrip(),
             userCarEntity.getPriceForMinute(),
             userCarEntity.getFinalPrice());
    }*/
}
