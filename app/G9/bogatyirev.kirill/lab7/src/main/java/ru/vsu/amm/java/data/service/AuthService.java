package ru.vsu.amm.java.data.service;

import ru.vsu.amm.java.domain.entities.Player;
import ru.vsu.amm.java.domain.repository.PlayerRepository;

import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthService {
    private final PlayerRepository playerRepository;
    private final PasswordEncodeService passwordEncodeService;

    private static final Logger log = Logger.getLogger(AuthService.class.getName());


    public AuthService() {
        this.playerRepository = new PlayerRepository();
        this.passwordEncodeService = new PasswordEncodeService();
    }

    public Player registration(String email, String login, String password) {

        try {
            String encodedPassword = passwordEncodeService.encode(password);
            log.info("Пароль закодирован");

            return playerRepository.registratePlayer(email, login, encodedPassword);
        } catch (SQLException e) {
            throw new RuntimeException("RegistrationService exception", e);
        }

    }

    public Player login(String login, String password) {

        try {

            Player player = playerRepository.findByLogin(login);
            log.info("Пользователь найден, его логин: " + player.getLogin());

            if(player == null) { return null; }

            if(passwordEncodeService.checkPassword(password, playerRepository.getPassword(player))) {
                log.info("Пароли совпали");
                return player;
            }

        } catch (SQLException e) {
            throw new RuntimeException("LoginService exception", e);
        }
        return null;
    }
}
