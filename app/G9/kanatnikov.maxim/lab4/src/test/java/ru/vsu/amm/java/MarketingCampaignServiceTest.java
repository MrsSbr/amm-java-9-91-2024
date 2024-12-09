package ru.vsu.amm.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.MarketingCampaign;
import ru.vsu.amm.java.enums.Type;
import ru.vsu.amm.java.service.MarketingCampaignService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MarketingCampaignServiceTest {
    private final List<MarketingCampaign> NULL_MARKETING_CAMPAIGN_LIST = null;
    private final List<MarketingCampaign> EMPTY_MARKETING_CAMPAIGN_LIST = new ArrayList<>();

    @Test
    public void testGetAverageCampaignDurabilityWithNull() {
        double result = MarketingCampaignService.getAverageCampaignDurability(NULL_MARKETING_CAMPAIGN_LIST);
        double expected = 0.0;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetAverageCampaignDurabilityWithEmpty() {
        double result = MarketingCampaignService.getAverageCampaignDurability(EMPTY_MARKETING_CAMPAIGN_LIST);
        double expected = 0.0;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetAverageCampaignDurability() {
        List<MarketingCampaign> marketingCampaignList = List.of(
                new MarketingCampaign(LocalDate.now().minusDays(500),
                        LocalDate.now().minusDays(100),
                        Type.Letter,
                        300,
                        500),
                new MarketingCampaign(LocalDate.now().minusDays(333),
                        LocalDate.now().minusDays(33),
                        Type.Advertisement,
                        400,
                        505),
                new MarketingCampaign(LocalDate.now().minusDays(200),
                        LocalDate.now(),
                        Type.Sms,
                        450,
                        505)
        );
        double result = MarketingCampaignService.getAverageCampaignDurability(marketingCampaignList);
        double expected = 300;
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testFindBestMarketingCampaignWithNull() {
        Set<MarketingCampaign> result = MarketingCampaignService
                .findBestMarketingCampaign(NULL_MARKETING_CAMPAIGN_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBestMarketingCampaignWithEmpty() {
        Set<MarketingCampaign> result = MarketingCampaignService.findBestMarketingCampaign(EMPTY_MARKETING_CAMPAIGN_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBestMarketingCampaign() {
        MarketingCampaign marketingCampaign = new MarketingCampaign(LocalDate.now().minusDays(500),
                LocalDate.now().minusDays(100),
                Type.Letter,
                300,
                500);

        List<MarketingCampaign> marketingCampaignList = List.of(
                marketingCampaign,
                new MarketingCampaign(LocalDate.now().minusDays(333),
                        LocalDate.now().minusDays(33),
                        Type.Advertisement,
                        400,
                        505),
                new MarketingCampaign(LocalDate.now().minusDays(200),
                        LocalDate.now(),
                        Type.Sms,
                        0,
                        505)
        );
        Set<MarketingCampaign> result = MarketingCampaignService.findBestMarketingCampaign(marketingCampaignList);
        Set<MarketingCampaign> expected = Set.of(marketingCampaign);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testFindBestMarketingCampaignReturnMoreThanOneCampaign() {
        MarketingCampaign marketingCampaign1 = new MarketingCampaign(LocalDate.now().minusDays(500),
                LocalDate.now().minusDays(100),
                Type.Letter,
                50,
                100);
        MarketingCampaign marketingCampaign2 = new MarketingCampaign(LocalDate.now().minusDays(500),
                LocalDate.now().minusDays(100),
                Type.Sms,
                750,
                1500);

        List<MarketingCampaign> marketingCampaignList = List.of(
                marketingCampaign1, marketingCampaign2,
                new MarketingCampaign(LocalDate.now().minusDays(333),
                        LocalDate.now().minusDays(33),
                        Type.Advertisement,
                        400,
                        505),
                new MarketingCampaign(LocalDate.now().minusDays(200),
                        LocalDate.now(),
                        Type.Sms,
                        0,
                        505)
        );
        Set<MarketingCampaign> result = MarketingCampaignService.findBestMarketingCampaign(marketingCampaignList);
        Set<MarketingCampaign> expected = Set.of(marketingCampaign1, marketingCampaign2);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetLastYearCampaignsTypesWithNull() {
        Set<Type> result = MarketingCampaignService.getLastYearCampaignsTypes(NULL_MARKETING_CAMPAIGN_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetLastYearCampaignsTypesWithEmpty() {
        Set<Type> result = MarketingCampaignService.getLastYearCampaignsTypes(EMPTY_MARKETING_CAMPAIGN_LIST);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetLastYearCampaignsTypes() {
        List<MarketingCampaign> marketingCampaignList = List.of(
                new MarketingCampaign(LocalDate.now().minusYears(3),
                        LocalDate.now().minusDays(33),
                        Type.Letter,
                        400,
                        505),
                new MarketingCampaign(LocalDate.now().minusDays(200),
                        LocalDate.now(),
                        Type.Sms,
                        0,
                        505),
                new MarketingCampaign(LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(1).minusMonths(2),
                        Type.Call,
                        10,
                        55),
                new MarketingCampaign(LocalDate.now().minusDays(20),
                        LocalDate.now().minusDays(3),
                        Type.Sms,
                        110,
                        50)
        );
        Set<Type> result = MarketingCampaignService.getLastYearCampaignsTypes(marketingCampaignList);
        Set<Type> expected = Set.of(Type.Letter, Type.Sms);
        Assertions.assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testGetLastYearCampaignsTypesReturnEmpty() {
        List<MarketingCampaign> marketingCampaignList = List.of(
                new MarketingCampaign(LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(1).minusMonths(2),
                        Type.Call,
                        10,
                        55),
                new MarketingCampaign(LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(3).minusDays(3),
                        Type.Sms,
                        110,
                        50)
        );
        Set<Type> result = MarketingCampaignService.getLastYearCampaignsTypes(marketingCampaignList);
        Assertions.assertTrue(result.isEmpty());
    }
}
