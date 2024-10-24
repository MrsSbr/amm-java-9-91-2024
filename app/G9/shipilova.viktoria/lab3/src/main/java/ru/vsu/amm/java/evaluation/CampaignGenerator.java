package ru.vsu.amm.java.evaluation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CampaignGenerator {
    public static List<Campaign> generateCampaigns(int numberOfCampaigns) {
        List<Campaign> campaigns = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfCampaigns; i++) {
            CampaignType type = CampaignType.values()[random.nextInt(CampaignType.values().length)];
            LocalDate startDate = LocalDate.now().minusDays(random.nextInt(365 * 5)); // За последние 5 лет
            LocalDate endDate = startDate.plusDays(random.nextInt(30)); // Длительность до 30 дней
            int reach = random.nextInt(9999) + 1; // охват
            double budget = random.nextDouble() * 100000;

            campaigns.add(new Campaign(type, startDate, endDate, reach, budget));
        }

        return campaigns;
    }
}
