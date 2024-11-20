package classes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.Plays;
import ru.vsu.amm.java.classes.TicketContainer;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TicketContainerTest {

    private TicketContainer container;

    @BeforeEach
    void setUp() {
        container = new TicketContainer();
    }

    @Test
    void returnTrueWhenFillCorrectValues() {
        List<String> performances = List.of("Спектакль 1", "Спектакль 2", "Спектакль 3");
        Scanner testScanner = new Scanner("10\n15\n20");

        container.fill(performances, testScanner, 45, "Введите кол-во билетов");

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
    void throwExceptionWhenFillCorrectValues() {
        List<String> performances = List.of("Спектакль 1", "Спектакль 2", "Спектакль 3");
        Scanner testScanner = new Scanner("-10\n15\n20");

        assertThrows(RuntimeException.class,
                () -> container.fill(performances, testScanner, 50, "Введите кол-во билетов")
        );
    }

    @Test
    void returnPopular() {
        List<Plays> playsList = List.of(
                new Plays("Спектакль 1", 100),
                new Plays("Спектакль 2", 100),
                new Plays("Спектакль 3", 0)
        );

        container = new TicketContainer(playsList);

        container.findPopular();

        assertEquals("Спектакль1", container.getPlays().get(0).getName());
        assertEquals("Спектакль2", container.getPlays().get(1).getName());

    }

    @Test
    void returnPlaysWithViewers() {
        List<Plays> playsList = List.of(
                new Plays("Спектакль 1", 100),
                new Plays("Спектакль 2", 100),
                new Plays("Спектакль 3", 10),
                new Plays("Спектакль 4", 0),
                new Plays("Спектакль 5", 0)

        );

        container = new TicketContainer(playsList);

        container.notNullPlays();

        assertEquals("Спектакль 1", container.getPlays().get(0).getName());
        assertEquals("Спектакль 2", container.getPlays().get(1).getName());
        assertEquals("Спектакль 3", container.getPlays().get(2).getName());

    }
}