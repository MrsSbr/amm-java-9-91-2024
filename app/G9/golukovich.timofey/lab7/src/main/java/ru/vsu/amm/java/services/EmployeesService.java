package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.EmployeeDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesService {
    EmployeeDto getEmployeeById(int employeeId, boolean isForUpdate);
    EmployeeDto getEmployeeByLogin(String employeeLogin, boolean isForUpdate);
    List<EmployeeDto> getAllEmployees(boolean isForUpdate);
    List<EmployeeDto> getAllFilteredEmployees(Integer employee_id, Integer hotel_id, String login,
                                              String name, String phone_number, String email,
                                              String passport_number, String passport_series,
                                              String post, LocalDate birthday, boolean isForUpdate);
    EmployeeDto patchEmployee(EmployeeDto employeeDto, String name, String phoneNumber, String email,
                              String passportNumber, String passportSeries, String rawBirthday);

}
