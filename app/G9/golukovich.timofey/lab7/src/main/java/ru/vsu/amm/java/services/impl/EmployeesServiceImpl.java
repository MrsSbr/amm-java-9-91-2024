package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.mappers.EmployeeDtoMapper;
import ru.vsu.amm.java.repository.EmployeeRepo;
import ru.vsu.amm.java.services.EmployeesService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class EmployeesServiceImpl implements EmployeesService {
    private static final Logger logger = Logger.getLogger(EmployeesServiceImpl.class.getName());

    private final EmployeeRepo employeeRepo;

    public EmployeesServiceImpl() {
        logger.info("EmployeesServiceImpl configuring");
        employeeRepo = new EmployeeRepo();
    }

    @Override
    public EmployeeDto getEmployeeById(int employeeId, boolean isForUpdate) {
        logger.info("get employee by id");

        try {
            var employeeEntityOpt = employeeRepo.getById(employeeId, isForUpdate);
            if (employeeEntityOpt.isPresent()) {
                return EmployeeDtoMapper.MapFromEntity(employeeEntityOpt.get());
            } else {
                final String message = "Employee not found by id " + employeeId;
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public EmployeeDto getEmployeeByLogin(String employeeLogin, boolean isForUpdate) {
        logger.info("get employee by id");

        try {
            var employeeEntityOpt = employeeRepo.getByLogin(employeeLogin, isForUpdate);
            if (employeeEntityOpt.isPresent()) {
                return EmployeeDtoMapper.MapFromEntity(employeeEntityOpt.get());
            } else {
                final String message = "Employee not found by login " + employeeLogin;
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<EmployeeDto> getAllEmployees(boolean isForUpdate) {
        logger.info("get all employees");

        try {
            return employeeRepo.getAll(isForUpdate).stream()
                    .map(EmployeeDtoMapper::MapFromEntity)
                    .toList();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<EmployeeDto> getAllFilteredEmployees(Integer employee_id, Integer hotel_id, String login,
                                                     String name, String phone_number, String email,
                                                     String passport_number, String passport_series,
                                                     String post, LocalDate birthday, boolean isForUpdate) {
        logger.info("get all filtered employees");

        try {
            return employeeRepo.getAllByParameters(employee_id, hotel_id,
                            login, name, phone_number, email,
                            passport_number, passport_series,
                            post, birthday, isForUpdate)
                    .stream()
                    .map(EmployeeDtoMapper::MapFromEntity)
                    .toList();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public EmployeeDto patchEmployee(EmployeeDto employeeDto, String name, String phoneNumber, String email,
                                     String passportNumber, String passportSeries, String rawBirthday) {
        var birthday = rawBirthday != null
                ? LocalDate.parse(rawBirthday, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                : null;

        try {
            var employees = employeeRepo.getAllByParameters(employeeDto.getId(), null, null,
                    null, phoneNumber, email, passportNumber, passportSeries, null, null, true);

            if (employees.size() == 1 && employees.getFirst().getId() == employeeDto.getId()) {
                var employeeEntity = employees.getFirst();
                employeeEntity = new EmployeeEntity(employeeEntity.getId(), employeeEntity.getHotelId(),
                        employeeEntity.getLogin(), employeeEntity.getPassword(), name, phoneNumber, email, passportNumber,
                        passportSeries, employeeEntity.getPost(), birthday);
                employeeRepo.update(employeeEntity);

                return EmployeeDtoMapper.MapFromEntity(employeeEntity);
            } else if (employees.isEmpty()) {
                final String message = "Employee with this id does not exists";
                logger.info(message);
                throw new EmployeeNotFoundException(message);
            } else {
                final String message = "Employee with this attributes already exists";
                logger.info(message);
                throw new EmployeeAlreadyExistsException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
