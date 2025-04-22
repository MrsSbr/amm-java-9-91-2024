package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.PasswordHasher;
import ru.vsu.amm.java.utils.Validator;

import java.util.UUID;


public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User register(String name, String email, String password){
        if (!Validator.isValidEmail(email)){
            throw new IllegalArgumentException("Некорректный email!");
        }
        if (!Validator.isValidName(name)){
            throw new IllegalArgumentException("Некорректное имя пользователя!");
        }
        if (password.length() < 8){
            throw new IllegalArgumentException("Пароль должен содержать минимум 8 символов");
        }
        if (userRepository.getByEmail(email) != null){
            throw new IllegalStateException("Пользователь с таким email уже существует!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(PasswordHasher.hashPassword(password));

        userRepository.save(user);
        return user;
    }

    @Override
    public User login(String email, String password){
        User user = userRepository.getByEmail(email);

        if (user == null){
            throw new SecurityException("Пользователь не найден");
        }
        if(!PasswordHasher.checkPasswordHash(password, user.getPassword())){
            throw new SecurityException("Неверный пароль!");
        }

        return user;
    }

    @Override
    public void updateUserName(UUID userID, String newName){
        User user = userRepository.getByID(userID);

        if (user == null){
            throw new IllegalArgumentException("Пользователь не найден");
        }

        user.setName(newName);
        userRepository.update(user);
    }

    @Override
    public void deleteUser(UUID userID){
        userRepository.delete(userID);
    }


}
