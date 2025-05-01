package repositories;

import configuration.TestDbConfiguration;
import org.junit.jupiter.api.AfterAll;
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

    @Test
    void allCrudOperations() throws SQLException {

        List<BoardGame> games = boardGameRepository.findAll();
        assertEquals(0, games.size());

        String name = "D&D";
        int price = 3990;
        Genre genre = Genre.RPG;
        int minAge = 12;
        String publisher = "HobbyWorld";
        String description = "Fantastic role-playing board game!";

        BoardGame game = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

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

        BoardGame cheap = new BoardGame(null, "Cheap", 1000,
                Genre.DUEL, 6, "A", "");
        BoardGame expensive = new BoardGame(null, "Expensive", 5000,
                Genre.WARGAME, 12, "B", "");

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

        String name = "D&D";
        int price = 3990;
        Genre genre = Genre.RPG;
        int minAge = 12;
        String publisher = "HobbyWorld";
        String description = "Fantastic role-playing board game!";

        BoardGame game1 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Carcassonne";
        price = 2490;
        genre = Genre.FAMILY;
        minAge = 3;
        publisher = "HobbyWorld";
        description = "";

        BoardGame game2 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Codenames";
        price = 1990;
        genre = Genre.DETECTIVE;
        minAge = 12;
        publisher = "Gaga";
        description = "Great card game";

        BoardGame game3 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findBy("publisher", "hobby");
        assertEquals(2, found.size());
    }

    @Test
    void findBy_ReturnEmptyList() throws SQLException {

        String name = "D&D";
        int price = 3990;
        Genre genre = Genre.RPG;
        int minAge = 12;
        String publisher = "HobbyWorld";
        String description = "Fantastic role-playing board game!";

        BoardGame game1 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Carcassonne";
        price = 2490;
        genre = Genre.FAMILY;
        minAge = 3;
        publisher = "HobbyWorld";
        description = "";

        BoardGame game2 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Codenames";
        price = 1990;
        genre = Genre.DETECTIVE;
        minAge = 12;
        publisher = "Gaga";
        description = "Great card game";

        BoardGame game3 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findBy("\"Name\"", "wrongName");
        assertEquals(0, found.size());
    }

    @Test
    void findByAge_ReturnList() throws SQLException {

        String name = "D&D";
        int price = 3990;
        Genre genre = Genre.RPG;
        int minAge = 12;
        String publisher = "HobbyWorld";
        String description = "Fantastic role-playing board game!";

        BoardGame game1 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Carcassonne";
        price = 2490;
        genre = Genre.FAMILY;
        minAge = 3;
        publisher = "HobbyWorld";
        description = "";

        BoardGame game2 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Codenames";
        price = 1990;
        genre = Genre.DETECTIVE;
        minAge = 12;
        publisher = "Gaga";
        description = "Great card game";

        BoardGame game3 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findByAge(10);
        assertEquals(1, found.size());
        assertEquals(game2.getName(), found.get(0).getName());
    }

    @Test
    void findByAge_ReturnEmptyList() throws SQLException {

        String name = "D&D";
        int price = 3990;
        Genre genre = Genre.RPG;
        int minAge = 12;
        String publisher = "HobbyWorld";
        String description = "Fantastic role-playing board game!";

        BoardGame game1 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Carcassonne";
        price = 2490;
        genre = Genre.FAMILY;
        minAge = 3;
        publisher = "HobbyWorld";
        description = "";

        BoardGame game2 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        name = "Codenames";
        price = 1990;
        genre = Genre.DETECTIVE;
        minAge = 12;
        publisher = "Gaga";
        description = "Great card game";

        BoardGame game3 = new BoardGame(null,
                name, price, genre, minAge, publisher, description);

        boardGameRepository.save(game1);
        boardGameRepository.save(game2);
        boardGameRepository.save(game3);

        List<BoardGame> found = boardGameRepository.findByAge(2);
        assertEquals(0, found.size());
    }
}
