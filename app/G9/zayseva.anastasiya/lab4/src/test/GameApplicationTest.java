import ru.vsu.amm.java.GameRecord;
import ru.vsu.amm.java.GameService;
import ru.vsu.amm.java.Genre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameApplicationTest {
    @Test
    public void testGetTopGenre() {
        List<GameRecord> records = Arrays.asList(
                new GameRecord("Game1", Genre.RPG, LocalDate.parse("2024-01-15"), 20, 5),
                new GameRecord("Game2", Genre.ADVENTURE, LocalDate.parse("2024-02-10"), 15, 4),
                new GameRecord("Game3", Genre.RPG, LocalDate.parse("2024-02-05"), 30, 5)
        );
        assertEquals(Genre.RPG, GameService.getTopGenre(records));
    }

    @Test
    public void testGetMonthWithMostHours() {
        List<GameRecord> records = Arrays.asList(
                new GameRecord("Game1", Genre.RPG, LocalDate.parse("2024-01-15"), 20, 5),
                new GameRecord("Game2", Genre.ADVENTURE, LocalDate.parse("2024-01-20"), 25, 4),
                new GameRecord("Game3", Genre.RPG, LocalDate.parse("2024-02-05"), 30, 5)
        );
        assertEquals(Month.JANUARY, GameService.getMonthWithMostHours(records));
    }

    @Test
    public void testGetRepeatedGames() {
        List<GameRecord> records = Arrays.asList(
                new GameRecord("Game1", Genre.RPG, LocalDate.parse("2024-01-15"), 20, 5),
                new GameRecord("Game2", Genre.STRATEGY, LocalDate.parse("2024-02-10"), 15, 4),
                new GameRecord("Game3", Genre.RPG, LocalDate.parse("2024-02-05"), 30, 5)
        );
        assertEquals(List.of("Game1"), GameService.getRepeatedGames(records));
    }

    @Test
    public void testGetTopGenreWithEmptyList(){
        List<GameRecord> records = List.of();
        assertNull(GameService.getTopGenre(records), "Top genre should be null when the list is empty.");
    }

    @Test
    public void testGetMonthWithMostHoursWithEmptyList(){
        List<GameRecord> records = List.of();
        assertNull(GameService.getMonthWithMostHours(records), "Month with most hours should be null when the list is empty.");
    }

    @Test
    public void testGetRepeatedGamesWithNoRepeats() {
        List<GameRecord> records = Arrays.asList(
                new GameRecord("Game1", Genre.RPG, LocalDate.parse("2024-01-15"), 20, 5),
                new GameRecord("Game2", Genre.STRATEGY, LocalDate.parse("2024-02-10"), 15, 4),
                new GameRecord("Game3", Genre.RPG, LocalDate.parse("2024-03-05"), 30, 5)
        );
        assertTrue(GameService.getRepeatedGames(records).isEmpty(), "There should be no repeated games.");
    }
}
