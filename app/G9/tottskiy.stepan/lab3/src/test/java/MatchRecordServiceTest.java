import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.MatchRecord;
import ru.vsu.amm.java.service.MatchRecordService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MatchRecordServiceTest {

    private MatchRecordService matchRecordService;
    private List<MatchRecord> records;

    @BeforeEach
    public void setup() {
        matchRecordService = new MatchRecordService();
        records = getSampleRecords();
    }

    @Test
    public void testGetMostFrequentBestPlayers() {
        Set<String> topPlayers = matchRecordService.getMostFrequentBestPlayers(records);
        Set<String> expected = Set.of("KIRILL");
        assertEquals(expected, topPlayers);
    }

    @Test
    public void testGetBestPlayersInAwayGames() {
        Set<String> awayBestPlayers = matchRecordService.getBestPlayersInAwayGames(records);
        Set<String> expected = Set.of("STEPA", "DIMA", "OLEG", "KIRILL");
        assertEquals(expected, awayBestPlayers);
    }

    @Test
    public void testGetSingleAwardedPlayersCount() {
        long singleAwardCount = matchRecordService.getSingleAwardedPlayersCount(records);
        assertEquals(4L, singleAwardCount);
    }


    @Test
    public void testGetSingleAwardedPlayersCountWithAllUnique() {
        List<MatchRecord> uniqueRecords = List.of(
                new MatchRecord("PLAYER1", "PLAYER2"),
                new MatchRecord("PLAYER3", "PLAYER4")
        );
        long count = matchRecordService.getSingleAwardedPlayersCount(uniqueRecords);
        assertEquals(4L, count);
    }

    @Test
    public void testGetMostFrequentBestPlayersWithEmptyList() {
     List<MatchRecord> emptyRecords =List.of();
     Set<String> result = matchRecordService.getMostFrequentBestPlayers(emptyRecords);
     assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBestPlayersWithNullRecords() {
        assertThrows(NullPointerException.class, () -> {
            matchRecordService.getMostFrequentBestPlayers(null);
        });
    }

    private List<MatchRecord> getSampleRecords() {
        return Arrays.asList(
                new MatchRecord("KIRILL", "STEPA"),
                new MatchRecord("KIRILL", "DIMA"),
                new MatchRecord("KIRILL", "OLEG"),
                new MatchRecord("NIKITA", "DIMA"),
                new MatchRecord("SASHA", "KIRILL")
        );
    }
}
