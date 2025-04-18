package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
        logger.info("Сервис пользователей инициализирован");
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("Сервис пользователей инициализирован с заданным репозиторием");
    }

    public List<UserEntity> getAllUsers() {
        logger.debug("Запрос на получение всех пользователей");
        List<UserEntity> users = userRepository.findAll();
        if (users == null) {
            logger.error("Ошибка при получении списка пользователей");
            return List.of();
        }
        logger.debug("Получено {} пользователей", users.size());
        return users;
    }

    public UserEntity getUserById(long userId) {
        logger.debug("Запрос пользователя по ID: {}", userId);
        UserEntity user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("Пользователь с ID {} не найден", userId);
        } else {
            logger.debug("Найден пользователь: {} {} (ID: {})",
                    user.getSurname(), user.getName(), userId);
        }
        return user;
    }

    public UserEntity getUserByEmail(String email) {
        logger.debug("Запрос пользователя по email: {}", email);
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("Пользователь с email {} не найден", email);
        } else {
            logger.debug("Найден пользователь: {} {} (email: {})",
                    user.getSurname(), user.getName(), email);
        }
        return user;
    }

    public void addUser(
            String name,
            String surname,
            String patronymic,
            LocalDate birthday,
            String email,
            String passwordHash,
            Role role
    ) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setBirthday(birthday);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);

        userRepository.upsert(user);
        logger.info("Добавлен новый пользователь: {} {} {} (email: {}, роль: {})",
                surname, name, patronymic, email, role);
    }
}