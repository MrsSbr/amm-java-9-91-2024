package ru.vsu.amm.java.service;

import ru.vsu.amm.java.Exceptions.UserNotFoundException;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserEntityRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserEntityRepository userEntityRepository;

    public AuthService() {
        userEntityRepository = new UserEntityRepository();
    }

    public void login(String name, String password) {
        try {
            Optional<UserEntity> user = userEntityRepository.findByLogin(name);
            if (user.isEmpty()) {
                throw new UserNotFoundException("User not found");
            }
            UserEntity userEntity = user.get();
            if (!userEntity.getPassword().equals(password)) {
                throw new UserNotFoundException("Error password");
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Database error");
        }
    }

    public void register(String name, String password, String confirmPassword, String email, String phoneNumber)  {
        try {
            if (userEntityRepository.findByLogin(name).isEmpty()) {
                if (password.equals(confirmPassword)) {
                    UserEntity userEntity = new UserEntity(name, password, email, phoneNumber);
                    userEntityRepository.save(userEntity);
                } else {
                    throw new UserNotFoundException("Passwords do not match");
                }
            } else {
                throw new UserNotFoundException("User is already registered");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAuth(HttpServletRequest req) {
        HttpSession httpSession = req.getSession(false);
        return httpSession != null && httpSession.getAttribute("user") != null;
    }

}
