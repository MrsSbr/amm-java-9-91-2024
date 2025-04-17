package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.CarEntity;
import ru.vsu.amm.java.enums.CarClass;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.model.dto.CarDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper {
    public static CarEntity resultSetToCarEntity(ResultSet rs) throws SQLException {
        return new CarEntity(rs.getLong("id"),
                rs.getString("manufacturer"),
                rs.getString("model"),
                rs.getInt("year"),
                Status.valueOf(rs.getString("status")),
                CarClass.valueOf(rs.getString("car_class")));
    }

    public static CarDto carEntityToCarDto(CarEntity ce) {
        return new CarDto(ce.getId(), ce.getManufacturer(),
                ce.getModel(),
                ce.getYear(),
                ce.getStatus(),
                ce.getCarClass());
    }

    public static CarEntity CarDtoToCarEntity(CarDto cd) {
        return new CarEntity(cd.getId(),
                cd.getManufacturer(),
                cd.getModel(),
                cd.getYear(),
                cd.getStatus(),
                cd.getCarClass());
    }
}
