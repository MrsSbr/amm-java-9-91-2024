package ru.vsu.amm.java.domain.repository;


import ru.vsu.amm.java.data.entities.DbPlayer;
import ru.vsu.amm.java.data.repository.DbPlayerRepository;
import ru.vsu.amm.java.domain.entities.Player;
import ru.vsu.amm.java.domain.mapper.PlayerMapper;

import java.sql.SQLException;
import java.util.logging.Logger;

public class PlayerRepository implements Repository<Player> {
    private final DbPlayerRepository dbPlayerRepository;
    private final PlayerMapper playerMapper;

    private static final Logger log = Logger.getLogger(PlayerRepository.class.getName());


    public PlayerRepository() {
        this.dbPlayerRepository = new DbPlayerRepository();
        this.playerMapper = new PlayerMapper();
    }

    @Override
    public Player findById(Long id) throws SQLException {

        try {
            DbPlayer dbPlayer = dbPlayerRepository.findById(id);
            return playerMapper.toDomain(dbPlayer);
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository create exception", e);
        }

    }

    @Override
    public void create(Player player) throws SQLException {

        try {
            DbPlayer dbPlayer = playerMapper.toData(player);
            dbPlayerRepository.create(dbPlayer);
            log.info("Пользователь создан");
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository create exception",e);
        }

    }

    @Override
    public void update(Player player) throws SQLException {

        try {
            DbPlayer dbPlayer = playerMapper.toData(player);
            dbPlayerRepository.update(dbPlayer);
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository update exception",e);
        }

    }

    @Override
    public void delete(Player player) throws SQLException {

        try {
            DbPlayer dbPlayer = playerMapper.toData(player);
            dbPlayerRepository.delete(dbPlayer);
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository delete exception",e);
        }

    }

    public Player findByLogin(String login) throws SQLException {

        try {
            DbPlayer dbPlayer = dbPlayerRepository.findByLogin(login);
            return playerMapper.toDomain(dbPlayer);
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository findByLogin exception",e);
        }
    }

    public Player registratePlayer(String email, String login, String password) throws SQLException {
        DbPlayer dbPlayer = new DbPlayer(login, password, email);
        dbPlayerRepository.create(dbPlayer);
        log.info("Пользователь зарегистрирован его логин: " + dbPlayer.getLogin());
        return playerMapper.toDomain(dbPlayer);
    }

    public String getPassword(Player player) throws SQLException {

        try {
            DbPlayer dbPlayer = playerMapper.toData(player);
            return dbPlayer.getPassword();
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository getPassword exception",e);
        }

    }

    public void setPassword (Player player, String password) throws SQLException {

        try {
            DbPlayer dbPlayer = playerMapper.toData(player);
            dbPlayer.setPassword(password);
        } catch (SQLException e) {
            throw new RuntimeException("PlayerRepository getPassword exception",e);
        }

    }
}
