package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.EmployeeRepository;
import ru.vsu.amm.java.Entities.Employee;

import java.sql.SQLException;
import java.time.LocalDate;

public class AuthService {

    private final EmployeeRepository employeeRepository;

    public AuthService() {
        this.employeeRepository = new EmployeeRepository();
    }

    public void login(String login, String password) {
        validateCredentials(login, password);

        try {
            Employee empl = employeeRepository.findByLogin(login)
                    .orElseThrow(
                    () -> new NotFoundException("User not found")
            );

            if (!empl.getPassword().equals(password)) {
                throw new NotFoundException("Invalid Password");
            }
        } catch (SQLException e) {
            throw new DbException("Database error during login", e);
        }
    }

    public void register(Employee employee) {
        validateCredentials(employee.getLogin(), employee.getPassword());
        validateAge(employee.getDateOfBirthEmpl());

        try {
            if (employeeRepository.findByLogin(employee.getLogin()).isEmpty()) {
                employeeRepository.save(employee);
            } else {
                throw new AlreadyExistException("User with this login already exists");
            }
        } catch (SQLException e) {
            throw new DbException("Database error during registration", e);
        }
    }

    private void validateAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        if (birthDate.plusYears(18).isAfter(now)) {
            throw new IllegalArgumentException("Employee must be older than 18 years");
        }
    }

    private void validateCredentials(String login, String password) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}