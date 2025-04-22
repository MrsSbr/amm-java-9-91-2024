package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.entities.BoardGame;

import java.sql.SQLException;
import java.util.List;

public interface BoardGamesService {
    List<BoardGame> getAllBoardGames() throws SQLException;
}
