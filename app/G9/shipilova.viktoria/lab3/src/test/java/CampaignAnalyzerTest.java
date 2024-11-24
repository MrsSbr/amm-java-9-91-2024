import java.util.HashSet;
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
import ru.vsu.amm.java.evaluation.CampaignType;

public class CampaignAnalyzerTest {
    private List<Campaign> campaigns;
    private Set<CampaignType> expectedCampaignTypes;

    @BeforeEach
    public void setup() {
        campaigns = Arrays.asList(
                new Campaign(CampaignType.SMS, LocalDate.now().minusDays(30), LocalDate.now().minusDays(15), 5000, 2000),
                new Campaign(CampaignType.MAIL, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 3000, 1500),
                new Campaign(CampaignType.CALLS, LocalDate.now().minusDays(40), LocalDate.now().minusDays(20), 0, 1000),
                new Campaign(CampaignType.MEDIA, LocalDate.now().minusDays(60), LocalDate.now().minusDays(20), 8000, 10)
        );
        expectedCampaignTypes = new HashSet<>();
        expectedCampaignTypes.add(CampaignType.SMS);
        expectedCampaignTypes.add(CampaignType.MAIL);
        expectedCampaignTypes.add(CampaignType.CALLS);
        expectedCampaignTypes.add(CampaignType.MEDIA);
    }

    @Test
    public void testCalculateAverageDuration() {
        double averageDuration = CampaignAnalyzer.calculateAverageDuration(campaigns);
        assertEquals(20, averageDuration, "The average campaign duration should be 20 days");
    }

    @Test
    public void testGetCampaignTypesLastYear() {
        Set<CampaignType> types = CampaignAnalyzer.getCampaignTypesLastYear(campaigns);
        assertNotNull(types, "Campaign types must not be null");
        assertEquals(4, types.size(), "There should be 4 types of campaigns");
        assertIterableEquals(expectedCampaignTypes, types, "There should be 4 types of campaigns");
    }

    @Test
    public void testFindBestCampaign() {
        Campaign bestCampaign = CampaignAnalyzer.findBestCampaign(campaigns);
        assertNotNull(bestCampaign, "The best campaign should not be null");
        assertEquals(CampaignType.MEDIA, bestCampaign.getType(), "The best campaign type should be 'Media'");
        assertTrue(bestCampaign.getReach() > 0, "The best campaign's reach should be greater than 0");
    }

    @Test
    public void testCalculateAverageDurationGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        assertNotNull(campaigns, "The average campaigns should not be null");
        double averageDuration = CampaignAnalyzer.calculateAverageDuration(campaigns);
        assertTrue(averageDuration > 0, "The average campaign duration should be greater than 0");
        assertFalse(campaigns.isEmpty(), "There should be no campaigns to calculate average");
    }

    @Test
    public void testGetCampaignTypesLastYearGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        Set<CampaignType> types = CampaignAnalyzer.getCampaignTypesLastYear(campaigns);
        assertNotNull(types, "Campaign types must not be null");
        assertFalse(types.isEmpty(), "There should be no campaign types to calculate average");
        assertNotNull(campaigns, "There should be no campaigns to calculate average");
        assertFalse(campaigns.isEmpty(), "There should be no campaigns to calculate average");
    }

    @Test
    public void testFindBestCampaignGenerated() {
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(100);
        assertNotNull(campaigns, "The best campaigns should not be null");
        assertFalse(campaigns.isEmpty(), "The best campaigns should not be empty");
        Campaign bestCampaign = CampaignAnalyzer.findBestCampaign(campaigns);
        assertNotNull(bestCampaign, "The best campaign should not be null");
        assertTrue(bestCampaign.getReach() > 0, "The best campaign's reach should be greater than 0");
    }

}
