package classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Fight;
import ru.vsu.amm.java.Tournaments;
import ru.vsu.amm.java.TournamentsManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class MortalCombatTest {
    private Tournaments tournaments;
    private TournamentsManager tournamentsManager;


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

        tournamentsManager = new TournamentsManager(tournaments);
    }

    @Test
    void returnTrueWhenFileIsExist() throws IOException {
        String path = "C:/Users/ASUS/IdeaProjects/amm-java-9-91-2024/app/G9/bogatyirev.kirill/lab4/src/main/java/ru/vsu/amm/java/res";

        tournaments = new Tournaments(path);

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
        assertTrue(tournamentsManager.allFightersInTournament(1)
                .containsAll(
                        Set.of("SubZero", "Scorpion", "Milena")
                ));
    }

    @Test
    void returnTrueWhenCountOfWinsNotNull() {
        assertTrue(tournamentsManager.countOfWins()
                .containsKey("SubZero"));
        assertTrue(tournamentsManager.countOfWins()
                .containsKey("Scorpion"));
        assertFalse(tournamentsManager.countOfWins()
                .containsKey("Milena"));
    }

    @Test
    void returnTrueWhenMonthWithMostFatalities() {
        assertTrue(tournamentsManager.monthWithMostFatality()
                .containsKey("DECEMBER"));

        assertFalse(tournamentsManager.monthWithMostFatality()
                .containsKey("MARCH"));
    }
}
