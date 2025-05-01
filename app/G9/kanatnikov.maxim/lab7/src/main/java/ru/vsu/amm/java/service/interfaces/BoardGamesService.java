package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.exceptions.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardGamesService {
    List<BoardGame> getAllBoardGames() throws SQLException;
    BoardGame getBoardGameById(Long id) throws SQLException, EntityNotFoundException;
    BoardGame getBoardGameByName(String name) throws SQLException, EntityNotFoundException;
    List<BoardGame> getBoardGameByField(String field, String value) throws SQLException;
    List<BoardGame> getBoardGameByPriceRange(int minPrice, int maxPrice) throws SQLException;
    List<BoardGame> getBoardGameByMinAge(int minAge) throws SQLException;
}
