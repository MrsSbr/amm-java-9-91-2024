package ru.vsu.amm.java.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.util.PasswordEncoder;

import javax.lang.model.element.Name;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String login, String password, HttpServletRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthException("Invalid login or password"));

        if (!PasswordEncoder.matches(password, user.getHashPassword())) {
            throw new AuthException("Invalid login or password");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
    }

    public void register(String name, String login, String password, HttpServletRequest request) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new AuthException("User with this login already exists");
        }

        String hashedPassword = PasswordEncoder.encode(password);

        User user = new User(name, login, hashedPassword);

        userRepository.save(user);
        user = userRepository.findByLogin(user.getLogin()).get();

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
