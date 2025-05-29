package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.EmployeeDto;

import java.util.Optional;

public interface AuthorizationService {
    Optional<EmployeeDto> login(String login, String password);
}
