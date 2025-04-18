package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.exceptions.EntityNotFoundException;
import ru.vsu.amm.java.repositories.BoardGameRepository;
import ru.vsu.amm.java.service.interfaces.BoardGamesService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
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

    @Override
    public BoardGame getBoardGameById(Long id) throws SQLException, EntityNotFoundException {
        var boardGame = boardGameRepository.findById(id);
        if (boardGame.isEmpty()) {
            logger.log(Level.SEVERE, "Board game with orderNumber " + id + " not found");
            throw new EntityNotFoundException("Board game not found");
        }
        return boardGame.get();
    }

    @Override
    public BoardGame getBoardGameByName(String name) throws SQLException, EntityNotFoundException {
        var boardGame = boardGameRepository.findByName(name);
        if (boardGame.isEmpty()) {
            logger.log(Level.SEVERE, "Board game with name " + name + " not found");
            throw new EntityNotFoundException("Board game not found");
        }
        return boardGame.get();
    }

    @Override
    public List<BoardGame> getBoardGameByField(String field, String value) throws SQLException {
        return boardGameRepository.findBy(field, value);

    }

    @Override
    public List<BoardGame> getBoardGameByPriceRange(int minPrice, int maxPrice) throws SQLException {
        return boardGameRepository.findByPrice(minPrice, maxPrice);
    }

    @Override
    public List<BoardGame> getBoardGameByMinAge(int minAge) throws SQLException {
        return boardGameRepository.findByAge(minAge);
    }
}
