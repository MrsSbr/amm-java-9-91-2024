package ru.vsu.amm.java.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.services.AuthorizationService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger logger = Logger.getLogger(AuthorizationServiceImpl.class.getName());

    private final EmployeeRepo employeeRepo;

    public AuthorizationServiceImpl() {
        logger.info("AuthorizationServiceImpl configuring");
        employeeRepo = new EmployeeRepo();
    }

    @Override
    public Optional<EmployeeDto> login(String login, String password) {
        logger.info("login " + login);
        try {
            var employee = employeeRepo.getByLogin(login)
                    .orElseThrow(
                            () -> {
                                final String message = "Employee " + login + " not exists, try again";
                                logger.info(message);
                                return new EmployeeNotFoundException(message);
                            }
                    );
            if (BCrypt.checkpw(password, employee.getPassword())) {
                return Optional.of(new EmployeeDto(employee.getId(), employee.getHotelId(), employee.getLogin(),
                        employee.getName(), employee.getPhoneNumber(), employee.getEmail(),
                        employee.getPassportSeries(), employee.getPassportNumber(), employee.getPost(),
                        employee.getSalary(), employee.getBirthday()));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
