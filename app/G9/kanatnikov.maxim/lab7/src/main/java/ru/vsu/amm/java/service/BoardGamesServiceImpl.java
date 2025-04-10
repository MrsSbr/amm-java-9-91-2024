package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.repositories.BoardGameRepository;
import ru.vsu.amm.java.service.interfaces.BoardGamesService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class BoardGamesServiceImpl implements BoardGamesService {
    private static final Logger logger = Logger.getLogger(BoardGamesServiceImpl.class.getName());
    private final BoardGameRepository boardGameRepository;

    public BoardGamesServiceImpl() {
        boardGameRepository = new BoardGameRepository();
    }

    @Override
    public List<BoardGame> getAllBoardGames() throws SQLException {
        return boardGameRepository.findAll();
    }
}
