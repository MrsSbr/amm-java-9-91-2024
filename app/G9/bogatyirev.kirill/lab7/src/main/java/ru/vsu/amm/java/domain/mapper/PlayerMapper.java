package ru.vsu.amm.java.domain.mapper;


import ru.vsu.amm.java.data.entities.DbPlayer;
import ru.vsu.amm.java.data.repository.DbPlayerRepository;
import ru.vsu.amm.java.domain.entities.Player;

import java.sql.SQLException;

public class PlayerMapper {
    private final DbPlayerRepository dbPlayerRepository;

    public PlayerMapper() {
        this.dbPlayerRepository = new DbPlayerRepository();
    }

    public Player toDomain(DbPlayer dbPlayer) {
        if (dbPlayer == null) {
            return null;
        }

        return new Player(
                dbPlayer.getId(),
                dbPlayer.getLogin(),
                dbPlayer.getEmail()
        );
    }

    public DbPlayer toData(Player player) throws SQLException {
        if(player == null) {
            return null;
        }

        Long id = player.getId();
        return dbPlayerRepository.findById(id);
    }
}
