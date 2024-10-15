import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.vsu.amm.java.evaluation.Campaign;
import ru.vsu.amm.java.evaluation.CampaignGenerator;
import ru.vsu.amm.java.evaluation.CampaignAnalyzer;

public class CampaignAnalyzerTest {
    private List<Campaign> campaigns;

    @BeforeEach
    public void setup() {
        campaigns = Arrays.asList(
                new Campaign("SMS", LocalDate.now().minusDays(30), LocalDate.now().minusDays(15), 5000, 2000),
                new Campaign("Mail", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 3000, 1500),
                new Campaign("Calls", LocalDate.now().minusDays(40), LocalDate.now().minusDays(20), 0, 1000),
                new Campaign("Media", LocalDate.now().minusDays(60), LocalDate.now().minusDays(20), 8000, 10)
        );
    }

    @Test
    public void testCalculateAverageDuration() {
        double averageDuration = CampaignAnalyzer.calculateAverageDuration(campaigns);
        assertEquals(20, averageDuration, "The average campaign duration should be 20 days");
    }

    @Test
    public void testGetCampaignTypesLastYear() {
        Set<String> types = CampaignAnalyzer.getCampaignTypesLastYear(campaigns);
        assertNotNull(types, "Campaign types must not be null");
        assertEquals(4, types.size(), "There should be 4 types of campaigns");
        assertTrue(types.contains("SMS"));
        assertTrue(types.contains("Mail"));
        assertTrue(types.contains("Calls"));
        assertTrue(types.contains("Media"));
    }

    @Test
    public void testFindBestCampaign() {
        Campaign bestCampaign = CampaignAnalyzer.findBestCampaign(campaigns);
        assertNotNull(bestCampaign, "The best campaign should not be null");
        assertEquals("Media", bestCampaign.getType(), "The best campaign type should be 'Media'");
        assertTrue(bestCampaign.getReach() > 0, "The best campaign's reach should be greater than 0");
    }

    @Test
    public void testCalculateAverageDurationGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        double averageDuration = CampaignAnalyzer.calculateAverageDuration(campaigns);
        assertTrue(averageDuration > 0);
    }

    @Test
    public void testGetCampaignTypesLastYearGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        Set<String> types = CampaignAnalyzer.getCampaignTypesLastYear(campaigns);
        assertNotNull(types);
    }

    @Test
    public void testFindBestCampaignGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        Campaign bestCampaign = CampaignAnalyzer.findBestCampaign(campaigns);
        assertNotNull(bestCampaign);
    }

}
