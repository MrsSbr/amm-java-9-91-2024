package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.CarEntity;
import ru.vsu.amm.java.enums.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper {
    public static CarEntity ResultSetToCarEntity(ResultSet rs) throws SQLException {
        return new CarEntity(rs.getLong("id"),
                rs.getString("manufacturer"),
                rs.getString("model"),
                rs.getInt("year"),
                Status.valueOf(rs.getString("status")));
    }
}
