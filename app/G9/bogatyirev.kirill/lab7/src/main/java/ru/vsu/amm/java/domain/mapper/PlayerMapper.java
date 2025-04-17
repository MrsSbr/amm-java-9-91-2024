package ru.vsu.amm.java.domain.mapper;

import main.data.entities.DbPlayer;
import main.domain.entities.Player;

import java.sql.SQLException;

public class PlayerMapper {
    public Player toDomain(DbPlayer dbPlayer) {
        if (dbPlayer == null) {
            return null;
        }

        return new Player(
                dbPlayer.getId(),
                dbPlayer.getLogin(),
                dbPlayer.getPassword(),
                dbPlayer.getEmail()
        );
    }

    public DbPlayer toData(Player player) throws SQLException {
        if(player == null) {
            return null;
        }


        return new DbPlayer(
                player.getId(),
                player.getLogin(),
                player.getPassword(),
                player.getEmail()
        );
    }
}
