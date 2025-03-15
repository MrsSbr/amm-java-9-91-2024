package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.AlreadyExistsException;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.exception.NotFoundException;
import ru.vsu.amm.java.repository.CustomerRepository;

import java.sql.SQLException;

public class AuthenticationService {

    private final CustomerRepository customerRepository;

    public AuthenticationService() {
        customerRepository = new CustomerRepository();
    }

    public void login(String name, String password) {
        try {
            Customer customer = customerRepository.findByName(name).orElseThrow(
                    () -> new NotFoundException("Ошибка входа")
            );
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void register(String name, String password) {
        try {
            if (customerRepository.findByName(name).isEmpty()) {
                Customer customer = new Customer(null, name, password); // TODO hash
                customerRepository.save(customer);
            } else {
                throw new AlreadyExistsException("Пользователь уже существует");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }
}
