package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.Dino;
import ru.vsu.amm.java.Entities.Employee;
import ru.vsu.amm.java.Enums.Kind;
import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.DinoRepository;
import ru.vsu.amm.java.Repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
    }

    public Employee createEmployee(Employee employee) {
        try {
            if (employeeRepository.findByLogin(employee.getLogin()).isPresent()) {
                throw new AlreadyExistException("Employee with this login already exists");
            }
            employeeRepository.save(employee);
            return employee;
        } catch (SQLException e) {
            throw new DbException("Error creating employee", e);
        }
    }

    public List<Employee> getAllEmployees() {
        try {
            return employeeRepository.findAll();
        } catch (SQLException e) {
            throw new DbException("Error fetching employees", e);
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Employee not found"));
        } catch (SQLException e) {
            throw new DbException("Error fetching employee", e);
        }
    }

    public Employee getEmployeeByLogin(String login) {
        try {
            return employeeRepository.findByLogin(login)
                    .orElseThrow(() -> new NotFoundException("Employee not found"));
        } catch (SQLException e) {
            throw new DbException("Error fetching employee by login", e);
        }
    }

    public void updateEmployee(Long id, Employee updatedEmployee) {
        try {
            Employee existing = employeeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Employee not found"));

            updatedEmployee.setIdEmpl(id);
            employeeRepository.update(updatedEmployee);
        } catch (SQLException e) {
            throw new DbException("Error updating employee", e);
        }
    }

    public void deleteEmployee(Long id) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Employee not found"));
            employeeRepository.delete(employee);
        } catch (SQLException e) {
            throw new DbException("Error deleting employee", e);
        }
    }
}

