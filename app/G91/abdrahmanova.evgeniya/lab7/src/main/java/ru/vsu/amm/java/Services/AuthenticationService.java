package ru.vsu.amm.java.Services;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.Dtos.UserDto;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidDateException;
import ru.vsu.amm.java.Exceptions.InvalidUserCredentialsException;
import ru.vsu.amm.java.Mappers.UserDtoMapper;
import ru.vsu.amm.java.Repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

public class AuthenticationService {
    private static final Logger log = Logger.getLogger(AuthenticationService.class.getName());

    private final UserRepository userRepository;

    public AuthenticationService() {
        userRepository = new UserRepository();
    }

    public UserDto login(String email, String password) {
        try {
            var user = userRepository.findByEmail(email).orElseThrow(
                    () -> new InvalidUserCredentialsException("Такого пользователя не существует")
            );

            if (BCrypt.checkpw(password, user.getPassword())) {
                return UserDtoMapper.mapFromEntity(user);
            }

            throw new InvalidUserCredentialsException("Неверный пароль");
        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserDto register(String email, String password, String fullName, String date, String phoneNumber, Role userRole) {
        try {
            var birthday = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (userRepository.findByEmail(email).isEmpty()) {
                var passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
                var newUser = new User(0, passwordHash, fullName, birthday, email, phoneNumber, userRole);
                userRepository.save(newUser);
                newUser = userRepository.findByEmail(email).orElseThrow(() -> new DatabaseException("Ошибка сохранения данных пользователя"));
                return UserDtoMapper.mapFromEntity(newUser);
            } else {
                log.severe("error WrongUserCredentialsException");
                throw new InvalidUserCredentialsException("Пользователь с таким логином уже существует");
            }
        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        } catch (DateTimeParseException e) {
            log.info("Ошибка даты");
            throw new InvalidDateException("Ошибка даты");
        }
    }
}
