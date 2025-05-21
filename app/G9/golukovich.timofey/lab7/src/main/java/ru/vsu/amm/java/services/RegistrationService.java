package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.EmployeeDto;

public interface RegistrationService {
    void registerEmployee(String login, String password, String rawPost, Integer hotelId, EmployeeDto adminDto);
    void unregisterEmployee(String login, EmployeeDto adminDto);
}
