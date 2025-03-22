package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.ScooterEntity;
import ru.vsu.amm.java.enums.ScooterStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScooterMapper {
    public static ScooterEntity ResultSetToCarEntity(ResultSet rs) throws SQLException {
        return new ScooterEntity(rs.getLong("id"),
                rs.getString("model"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                ScooterStatus.valueOf(rs.getString("status")));
    }
}
