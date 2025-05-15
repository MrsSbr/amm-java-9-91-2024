package repositories;

import configuration.TestDbConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.repositories.BoardGameRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardGameRepositoryIntegrationTest {

    private BoardGameRepository boardGameRepository;
    private static List<BoardGame> testGames;

    @BeforeAll
    static void setUpAll() {
        testGames = List.of(
                new BoardGame(null, "D&D", 3990, Genre.RPG, 12, "HobbyWorld",
                        "Fantastic role-playing board game!"),
                new BoardGame(null, "Cheap", 1000, Genre.DUEL, 6, "A", ""),
                new BoardGame(null, "Expensive", 5000, Genre.WARGAME, 12, "B", ""),
                new BoardGame(null, "Carcassonne", 2490, Genre.FAMILY, 3, "HobbyWorld", ""),
                new BoardGame(null, "Codenames", 1990, Genre.DETECTIVE, 12, "Gaga", "Great card game")
        );
    }

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = TestDbConfiguration.getDataSource();
        boardGameRepository = new BoardGameRepository(dataSource);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM boardgame");
        }
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        DataSource dataSource = TestDbConfiguration.getDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM boardgame");
        }
    }

    private BoardGame copyBoardGame(BoardGame original) {
        return new BoardGame(
                null,
                original.getName(),
                original.getPrice(),
                original.getGenre(),
                original.getMinAge(),
                original.getPublisher(),
                original.getDescription()
        );
    }

    @Test
    void allCrudOperations() throws SQLException {

        List<BoardGame> games = boardGameRepository.findAll();
        assertEquals(0, games.size());

        BoardGame game = copyBoardGame(testGames.get(0));

        boardGameRepository.save(game);

        games = boardGameRepository.findAll();
        assertEquals(1, games.size());

        game = games.get(0);
        game.setMinAge(16);
        boardGameRepository.update(game);

        Optional<BoardGame> updatedGame = boardGameRepository.findById(game.getBoardGameId());
        assertTrue(updatedGame.isPresent());
        assertEquals(16, updatedGame.get().getMinAge());

        boardGameRepository.delete(updatedGame.get());
        games = boardGameRepository.findAll();
        assertEquals(0, games.size());
    }

    @Test
    void findByPrice_ShouldReturnListOfGames() throws SQLException {

        BoardGame cheap = copyBoardGame(testGames.get(1));
        BoardGame expensive = copyBoardGame(testGames.get(2));

        boardGameRepository.save(cheap);
        boardGameRepository.save(expensive);

        List<BoardGame> result = boardGameRepository.findByPrice(1500, 4500);
        assertEquals(0, result.size());

        result = boardGameRepository.findByPrice(900, 1500);
        assertEquals(1, result.size());
        Optional<BoardGame> resultGame = boardGameRepository.findByName("Cheap");
        assertTrue(resultGame.isPresent());

        result = boardGameRepository.findByPrice(900, 5500);
        assertEquals(2, result.size());
    }

    @Test
    void findBy_ShouldBeCaseInsensitive() throws SQLException {

        BoardGame game1 = copyBoardGame(testGames.get(0));
        BoardGame game2 = copyBoardGame(testGames.get(3));
        BoardGame game3 = copyBoardGame(testGames.get(4));

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findBy("publisher", "hobby");
        assertEquals(2, found.size());
    }

    @Test
    void findBy_ReturnEmptyList() throws SQLException {

        BoardGame game1 = copyBoardGame(testGames.get(0));
        BoardGame game2 = copyBoardGame(testGames.get(3));
        BoardGame game3 = copyBoardGame(testGames.get(4));

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findBy("\"Name\"", "wrongName");
        assertEquals(0, found.size());
    }

    @Test
    void findByAge_ReturnList() throws SQLException {

        BoardGame game1 = copyBoardGame(testGames.get(0));
        BoardGame game2 = copyBoardGame(testGames.get(3));
        BoardGame game3 = copyBoardGame(testGames.get(4));

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findByAge(10);
        assertEquals(1, found.size());
        assertEquals(game2.getName(), found.get(0).getName());
    }

    @Test
    void findByAge_ReturnEmptyList() throws SQLException {

        BoardGame game1 = copyBoardGame(testGames.get(0));
        BoardGame game2 = copyBoardGame(testGames.get(3));
        BoardGame game3 = copyBoardGame(testGames.get(4));

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findByAge(2);
        assertEquals(0, found.size());
    }
}
