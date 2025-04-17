package ru.vsu.amm.java.data.service;

import main.domain.entities.Player;
import main.domain.repository.PlayerRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class AuthService {
    private final PlayerRepository playerRepository;

    public AuthService() {
        this.playerRepository = new PlayerRepository();
    }

    public Player registration(String email, String login, String password) {
        try {
            String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            return playerRepository.registratePlayer(email, login, encodedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
