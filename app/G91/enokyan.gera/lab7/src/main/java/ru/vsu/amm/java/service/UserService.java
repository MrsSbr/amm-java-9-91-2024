package ru.vsu.amm.java.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
        logger.log(Level.INFO, "Сервис пользователей инициализирован");
    }

    public void addUser(String nickname, String password, List<Role> roles) {
        logger.log(Level.FINE, MessageFormat.format(
                "Запрос на добавление нового пользователя с nickname={0}, roles={1}",
                nickname,
                roles)
        );
        UserEntity user = new UserEntity();
        user.setNickname(nickname);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRating(1000d);
        user.setRoles(roles);

        userRepository.create(user);
        logger.log(Level.FINE, MessageFormat.format("Добавлен пользователь с nickname={0}, roles={1}", nickname, roles));
    }

    public List<UserEntity> getAllUsers() {
        logger.log(Level.FINE, "Запрос на получение всех пользователей");
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.log(Level.WARNING, "Не получено ни одного пользователя");
        } else {
            logger.log(Level.FINE, MessageFormat.format("Получено пользователей: {0}", users.size()));
        }
        return users;
    }

    public List<UserEntity> getTopRatedUsers(long count) {
        logger.log(Level.FINE, MessageFormat.format("Запрос на получение топ-{0} пользователей", count));
        List<UserEntity> users = userRepository.findTopRated(count);
        if (users.isEmpty()) {
            logger.log(Level.WARNING, "Не получено ни одного пользователя");
        } else {
            logger.log(Level.FINE, MessageFormat.format("Получен топ-{0} пользователей", users.size()));
        }
        return users;
    }

    public UserEntity getUserByNickname(String nickname) {
        logger.log(Level.FINE, MessageFormat.format("Запрос на получение пользователя с nickname={0}", nickname));
        UserEntity user = userRepository.findByNickname(nickname);
        if (user == null) {
            logger.log(Level.WARNING, MessageFormat.format("Пользователь с nickname={0} не получен", nickname));
        } else {
            logger.log(Level.FINE, MessageFormat.format("Пользователь с nickname={0} получен", nickname));
        }
        return user;
    }
}
