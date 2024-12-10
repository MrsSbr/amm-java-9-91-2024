package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.MarketingCampaign;
import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MarketingCampaignService {
    private MarketingCampaignService() {
    }

    public static double getAverageCampaignDurability(List<MarketingCampaign> marketingCampaigns) {
        if (marketingCampaigns == null || marketingCampaigns.isEmpty()) {
            return 0.0;
        }

        return marketingCampaigns.stream()
                .mapToLong(c -> ChronoUnit.DAYS.between(c.dateStart(), c.dateEnd()))
                .average()
                .orElse(0.0);
    }

    public static Set<Type> getLastYearCampaignsTypes(List<MarketingCampaign> marketingCampaigns) {
        if (marketingCampaigns == null || marketingCampaigns.isEmpty()) {
            return Set.of();
        }

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return marketingCampaigns.stream()
                .filter(c -> c.dateEnd().isAfter(oneYearAgo))
                .map(MarketingCampaign::type)
                .collect(Collectors.toSet());
    }

    public static Set<MarketingCampaign> findBestMarketingCampaign(List<MarketingCampaign> marketingCampaigns) {
        if (marketingCampaigns == null || marketingCampaigns.isEmpty()) {
            return Set.of();
        }

        double maxRatio = marketingCampaigns.stream()
                .map(MarketingCampaignService::findCampaignRatio)
                .max(Comparator.comparingDouble(Double::doubleValue))
                .orElse(0.0);

        return marketingCampaigns.stream()
                .filter(c -> findCampaignRatio(c) == maxRatio)
                .collect(Collectors.toSet());
    }

    private static double findCampaignRatio(MarketingCampaign marketingCampaign) {
        return marketingCampaign.coverage() == 0
                ? 0.0
                : (double) marketingCampaign.budget() / marketingCampaign.coverage();
    }
}
