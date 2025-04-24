package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.entities.EmployeeEntity;

public class EmployeeDtoMapper {
    public static EmployeeDto MapFromEntity(EmployeeEntity e)
    {
        return new EmployeeDto(e.getId(), e.getHotelId(), e.getLogin(),
                e.getName(), e.getPhoneNumber(), e.getEmail(), e.getPassportSeries(),
                e.getPassportNumber(), e.getPost(), e.getSalary(), e.getBirthday());
    }
}
