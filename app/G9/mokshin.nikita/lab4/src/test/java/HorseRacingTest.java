import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.HorseRacing;
import ru.vsu.amm.java.entity.HorseStatistics;
import ru.vsu.amm.java.service.HorseRacingService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HorseRacingTest {

    private static final String TEST_FILE_PATH = "test_horseRacing.txt";

    @Test
    void testLoadFromFile() throws Exception {
        Files.write(Path.of(TEST_FILE_PATH), List.of(
                "2024-01-10;Blizzard;Dawn;Breeze",
                "2024-01-15;Blizzard;Blackie;Dawn",
                "2024-01-20;Breeze;Blizzard;Rainbow",
                "2024-01-25;Thunder;Breeze;Dawn"
        ));

        List<HorseRacing> races = HorseRacingService.loadFromFile(TEST_FILE_PATH);

        assertEquals(4, races.size());
        assertEquals("Blizzard", races.get(0).getFirstPlaceNameHorse());
        assertEquals("Dawn", races.get(0).getSecondPlaceNameHorse());
        assertEquals("Breeze", races.get(0).getThirdPlaceNameHorse());

        Files.delete(Path.of(TEST_FILE_PATH));
    }

    @Test
    void testGenerateToFile() throws Exception {
        HorseRacingService.generateToFile(TEST_FILE_PATH, 5);

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        Files.delete(Path.of(TEST_FILE_PATH));
    }

    @Test
    void testFindMostFrequentHorse() {
        List<HorseRacing> races = List.of(
                new HorseRacing(LocalDate.of(2024, 1, 10), "Blizzard", "Dawn", "Breeze"),
                new HorseRacing(LocalDate.of(2024, 1, 15), "Blizzard", "Blackie", "Dawn"),
                new HorseRacing(LocalDate.of(2024, 1, 20), "Breeze", "Blizzard", "Rainbow"),
                new HorseRacing(LocalDate.of(2024, 1, 25), "Thunder", "Breeze", "Blizzard")
        );

        String mostFrequentHorse = HorseRacingService.findMostFrequentHorse(races);

        assertEquals("Blizzard", mostFrequentHorse);
    }

    @Test
    void testFindMostSuccessfulHorse() {
        List<HorseRacing> races = List.of(
                new HorseRacing(LocalDate.of(2024, 1, 10), "Blizzard", "Dawn", "Breeze"),
                new HorseRacing(LocalDate.of(2024, 1, 15), "Blizzard", "Blackie", "Dawn"),
                new HorseRacing(LocalDate.of(2024, 1, 20), "Breeze", "Blizzard", "Rainbow"),
                new HorseRacing(LocalDate.of(2024, 1, 25), "Thunder", "Breeze", "Dawn")
        );

        String mostSuccessfulHorse = HorseRacingService.findMostSuccessfulHorse(races);

        assertEquals("Blizzard", mostSuccessfulHorse);
    }

    @Test
    void testCalculateHorseStatistics() {
        List<HorseRacing> races = List.of(
                new HorseRacing(LocalDate.of(2024, 1, 10), "Blizzard", "Dawn", "Breeze"),
                new HorseRacing(LocalDate.of(2024, 1, 15), "Blizzard", "Blackie", "Dawn"),
                new HorseRacing(LocalDate.of(2024, 1, 20), "Breeze", "Blizzard", "Rainbow"),
                new HorseRacing(LocalDate.of(2024, 1, 25), "Thunder", "Breeze", "Dawn")
        );

        Map<String, HorseStatistics> stats = HorseRacingService.calculateHorseStatistics(races);

        HorseStatistics blizzardStats = stats.get("Blizzard");
        assertNotNull(blizzardStats);
        assertEquals(3, blizzardStats.getTotalRaces());
        assertEquals(2, blizzardStats.getFirstPlaces());
        assertEquals(1, blizzardStats.getSecondPlaces());
        assertEquals(0, blizzardStats.getThirdPlaces());

        HorseStatistics dawnStats = stats.get("Dawn");
        assertNotNull(dawnStats);
        assertEquals(3, dawnStats.getTotalRaces());
        assertEquals(0, dawnStats.getFirstPlaces());
        assertEquals(1, dawnStats.getSecondPlaces());
        assertEquals(2, dawnStats.getThirdPlaces());
    }
}
