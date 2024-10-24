package ru.vsu.amm.java.evaluation;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;

public class CampaignAnalyzer {
    public static double calculateAverageDuration(List<Campaign> campaigns) {
        return campaigns.stream()
                .mapToLong(Campaign::getDuration)
                .average()
                .orElse(0);
    }

    public static Set<CampaignType> getCampaignTypesLastYear(List<Campaign> campaigns) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return campaigns.stream()
                .filter(c -> c.getStartDate().isAfter(oneYearAgo))
                .map(Campaign::getType)
                .collect(Collectors.toSet());
    }

    public static Campaign findBestCampaign(List<Campaign> campaigns) {
        return campaigns.stream()
                .min(Comparator.comparingDouble(c -> c.getBudget() / c.getReach()))
                .orElse(null);
    }
}
