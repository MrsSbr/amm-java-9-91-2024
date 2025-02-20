package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entity.MarketingCampaign;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketingCampaignFactory {
    private final static Type[] TYPES = Type.values();
    private final static int DAYS = 1000;

    private MarketingCampaignFactory() {
    }

    public static MarketingCampaign generateMarketingCampaign() {
        Random random = new Random();
        int daysAfterStart = random.nextInt(DAYS);
        LocalDate dateStart = LocalDate.now().minusDays(daysAfterStart);
        LocalDate dateEnd = dateStart.plusDays(random.nextInt(daysAfterStart));
        Type type = TYPES[random.nextInt(TYPES.length)];
        int coverage = random.nextInt();
        int budget = random.nextInt();
        return new MarketingCampaign(dateStart, dateEnd, type, coverage, budget);
    }

    public static List<MarketingCampaign> generateMarketingCampaign(int count) {
        List<MarketingCampaign> marketingCampaignList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            var marketingCampaign = generateMarketingCampaign();
            marketingCampaignList.add(marketingCampaign);
        }

        return marketingCampaignList;
    }
}
