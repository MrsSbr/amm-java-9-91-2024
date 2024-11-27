package classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.PerformanceManager;
import ru.vsu.amm.java.classes.PerformanceUtils;
import ru.vsu.amm.java.classes.Plays;
import ru.vsu.amm.java.classes.PerformanceContainer;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceContainerTest {

    private PerformanceContainer container;
    private PerformanceManager manager;
    private PerformanceUtils util;

    @BeforeEach
    void setUp() {
        manager = new PerformanceManager();
        container = new PerformanceContainer();
        util = new PerformanceUtils();
    }

    @Test
    void returnTrueWhenFillCorrectValues() throws Exception {
        List<String> performances = List.of("Спектакль 1", "Спектакль 2", "Спектакль 3");
        Scanner testScanner = new Scanner("10\n15\n20");

        manager.fill(performances, testScanner, 45, "Введите кол-во билетов");

        assertEquals(3, container.getSize());

        assertTrue(container.getPlays().containsAll(
                List.of(
                        new Plays("Спектакль 1", 10),
                        new Plays("Спектакль 2", 15),
                        new Plays("Спектакль 3", 20)
                )
        ));
    }

    @Test
    void throwExceptionWhenFillIncorrectValues() {
        List<String> performances = List.of("Спектакль 1", "Спектакль 2", "Спектакль 3");
        Scanner testScanner = new Scanner("-10\n15\n20");

        assertThrows(Exception.class,
                () -> manager.fill(performances, testScanner, 50, "Введите кол-во билетов")
        );
    }

    @Test
    void throwExceptionWhenFillToMuchTickets() {
        List<String> performances = List.of("Спектакль 1", "Спектакль 2", "Спектакль 3");
        Scanner testScanner = new Scanner("10000\n15000\n2000");

        assertThrows(Exception.class,
                () -> manager.fill(performances, testScanner, 24000, "Введите кол-во билетов")
        );
    }

    @Test
    void returnPopular() {
        List<String> playsList = List.of(
                "Спектакль 1",
                "Спектакль 2",
                "Спектакль 3"
        );

        manager.fillRandomly(playsList, 10000);

        util = new PerformanceUtils(manager.getPlays());

        List<String> popularPerformances = util.findPopular();

        assertFalse(popularPerformances.isEmpty());
    }

    @Test
    void returnTrueWhenPlaysWithViewers() {
        List<String> playsList = List.of(
                "Спектакль 1",
                "Спектакль 2",
                "Спектакль 3",
                "Спектакль 4",
                "Спектакль 5"
        );

        manager.fillRandomly(playsList, 10000);

        util = new PerformanceUtils(manager.getPlays());

        List<String> performancesWithViewers = util.notNullPlays();

        assertFalse(performancesWithViewers.isEmpty());
    }

    @Test
    void returnTrueWhenHaveNoViewers() {
        List<Plays> playsList = List.of(
                new Plays("Спектакль 1", 0),
                new Plays("Спектакль 2", 0),
                new Plays("Спектакль 3", 0),
                new Plays("Спектакль 4", 0),
                new Plays("Спектакль 5", 0)
        );

        util = new PerformanceUtils(playsList);

        List<String> performancesWithViewers = util.notNullPlays();

        assertTrue(performancesWithViewers.isEmpty());
    }
}