package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.MarketingCampaign;
import ru.vsu.amm.java.service.MarketingCampaignService;
import ru.vsu.amm.java.utils.FileWorker;
import ru.vsu.amm.java.utils.MarketingCampaignFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarketingCampaignApplication {
    private static final int COUNT_CAMPAIGNS = 9456;
    private static final String AVERAGE_DURABILITY_MESSAGE = "Average campaign durability: ";
    private static final String LAST_YEAR_CAMPAIGNS_TYPES_MESSAGE = "Last year campaigns types";
    private static final String BEST_MARKETING_CAMPAIGN_MESSAGE = "Best marketing campaign";
    private static final Logger LOGGER = Logger.getLogger(MarketingCampaignApplication.class.getName());
    private static final String FILE_PATH =
            "app/G9/kanatnikov.maxim/lab4/src/main/java/ru/vsu/amm/java/resources/marketingCampaigns.txt";
    public static void main(String[] args) {
        List<MarketingCampaign> marketingCampaigns = MarketingCampaignFactory
                .generateMarketingCampaign(COUNT_CAMPAIGNS);
        try {
            FileWorker.write(marketingCampaigns, FILE_PATH);
            marketingCampaigns = FileWorker.read(FILE_PATH);

            System.out.println(AVERAGE_DURABILITY_MESSAGE
                    + MarketingCampaignService.getAverageCampaignDurability(marketingCampaigns));

            System.out.println(LAST_YEAR_CAMPAIGNS_TYPES_MESSAGE);
            MarketingCampaignService.getLastYearCampaignsTypes(marketingCampaigns)
                    .forEach(System.out::println);

            System.out.println(BEST_MARKETING_CAMPAIGN_MESSAGE);
            MarketingCampaignService.findBestMarketingCampaign(marketingCampaigns)
                    .forEach(System.out::println);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
            System.out.println(e.toString());
        }
    }
}