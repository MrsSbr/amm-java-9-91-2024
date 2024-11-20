import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.MatchRecord;
import ru.vsu.amm.java.service.MatchRecordService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertNotNull(topPlayers);
        assertTrue(topPlayers.contains("KIRILL"));
    }

    @Test
    public void testGetBestPlayersInAwayGames() {
        Set<String> awayBestPlayers = matchRecordService.getBestPlayersInAwayGames(records);
        assertNotNull(awayBestPlayers);
        assertTrue(awayBestPlayers.contains("DIMA"));
    }

    @Test
    public void testGetSingleAwardedPlayersCount() {
        long singleAwardCount = matchRecordService.getSingleAwardedPlayersCount(records);
        assertEquals(4L, singleAwardCount);
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
