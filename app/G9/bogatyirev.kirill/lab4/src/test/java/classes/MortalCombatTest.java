package classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Fight;
import ru.vsu.amm.java.Tournaments;
import ru.vsu.amm.java.TournamentsManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MortalCombatTest {
    private Tournaments tournaments;
    private TournamentsManager tournamentsManager;
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "res";


    @BeforeEach
    void setUp() {
        tournaments = new Tournaments(List.of(
                new Fight(1, "SubZero", "Scorpion",
                        "SubZero",true, LocalDate.now()),
                new Fight(1, "Scorpion", "Milena",
                        "Scorpion",true, LocalDate.now()),
                new Fight(2, "Jonny Cage", "Sonya Blade",
                        "Sonya Blade", true, LocalDate.now())
        ));

        tournamentsManager = new TournamentsManager();
    }

    @Test
    void returnTrueWhenFileIsExist() throws IOException {

        tournaments = new Tournaments(FILE_PATH);

        assertFalse(tournaments.getFights().isEmpty());
    }

    @Test
    void throwExceptionWhenFileIsNotExists() throws IOException {
        String path = "";

        assertThrows(IOException.class, () -> {
            tournaments = new Tournaments(path);

            tournaments.getFights();
        }) ;
    }

    @Test
    void returnTrueWhenAllFightersInTournament() {
        assertTrue(tournamentsManager.allFightersInTournament(1, tournaments)
                .containsAll(
                        Set.of("SubZero", "Scorpion", "Milena")
                ));
    }

    @Test
    void emptyTournamentListTest() {
        Tournaments tournaments1 = new Tournaments(new ArrayList<>());

        try {
            assertTrue(tournamentsManager.countOfWins(tournaments1).isEmpty());
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }

        try {
            assertTrue(tournamentsManager.allFightersInTournament(1, tournaments1).isEmpty());
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }

    }

    @Test
    void NoFightersInTournamentTest() {
        assertTrue(tournamentsManager.allFightersInTournament(5, tournaments).isEmpty());
    }

    @Test
    void noFightsWithFatalityEarlyThenThreeYearsAgo() throws IOException {
        Tournaments tournaments1 = new Tournaments(FILE_PATH);

        Map<String, Integer> result = tournamentsManager.monthWithMostFatality(tournaments1);

        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

        assertTrue(result.keySet().stream()
                .allMatch(month -> tournaments1.getFights().stream()
                        .anyMatch(fight -> fight.getFightDate().isAfter(threeYearsAgo)
                        && fight.isFatality()
                        )));
    }

    @Test
    void returnTrueWhenCountOfWinsNotNull() {
        assertTrue(tournamentsManager.countOfWins(tournaments)
                .containsKey("SubZero"));
        assertTrue(tournamentsManager.countOfWins(tournaments)
                .containsKey("Scorpion"));
        assertFalse(tournamentsManager.countOfWins(tournaments)
                .containsKey("Milena"));
    }

    @Test
    void returnTrueWhenMonthWithMostFatalities() {
        assertTrue(tournamentsManager.monthWithMostFatality(tournaments)
                .containsKey("DECEMBER"));

        assertFalse(tournamentsManager.monthWithMostFatality(tournaments)
                .containsKey("MARCH"));
    }
}
