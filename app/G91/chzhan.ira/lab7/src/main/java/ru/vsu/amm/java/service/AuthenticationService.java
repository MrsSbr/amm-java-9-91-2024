package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.AlreadyExistsException;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.exception.NotFoundException;
import ru.vsu.amm.java.repository.CustomerRepository;
import org.mindrot.jbcrypt.BCrypt; // добавьте эту зависимость в pom.xml или build.gradle

import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationService {

    private final CustomerRepository customerRepository;

    public AuthenticationService() {
        customerRepository = new CustomerRepository();
    }

    public Optional<Customer> login(String name, String password) {
        try {
            Optional<Customer> customerOptional = customerRepository.findByName(name);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                if (BCrypt.checkpw(password, customer.getPassword())) {
                    return Optional.of(customer); // Успешная аутентификация
                } else {
                    return Optional.empty(); // Неверный пароль
                }
            } else {
                return Optional.empty(); // Пользователь не найден
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void register(String name, String password) {
        try {
            if (customerRepository.findByName(name).isEmpty()) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                Customer customer = new Customer(null, name, hashedPassword); // хешируем пароль
                customerRepository.save(customer);
            } else {
                throw new AlreadyExistsException("Пользователь уже существует");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }
}