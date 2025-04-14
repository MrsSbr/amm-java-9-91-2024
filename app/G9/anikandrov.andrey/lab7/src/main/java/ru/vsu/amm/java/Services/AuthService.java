package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.UserRepository;

import java.sql.SQLException;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {
        userRepository = new UserRepository();
    }

    public AuthService(UserRepository userRep) {
        userRepository = userRep;
    }

    public void login(String name, String password) {
        try {
            UserEntity user = userRepository.findByUserName(name).orElseThrow(
                    () -> new NotFoundException("Login Error")
            );

            if (!user.getUserPassword().equals(password)) {
                throw new NotFoundException("Invalid Password");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void register(String name, String password) {
        try {
            if (userRepository.findByUserName(name).isEmpty()) {
                UserEntity customer = new UserEntity(name, password);
                userRepository.save(customer);
            } else {
                throw new AlreadyExistException("User With Such UserName Already Exist");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}