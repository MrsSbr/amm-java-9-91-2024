package ru.vsu.amm.java;

import ru.vsu.amm.java.logger.LoggerConfig;
import ru.vsu.amm.java.services.Deals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Month;
import java.util.Map;
import java.util.logging.Logger;

import static ru.vsu.amm.java.services.DealsAnalyzer.collectIncomeByClients;
import static ru.vsu.amm.java.services.DealsAnalyzer.findMostEffectiveManagerLastMonth;
import static ru.vsu.amm.java.services.DealsAnalyzer.findMostProfitableMonth;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, URISyntaxException {
        LoggerConfig.configure();
        try {
            Deals deals = new Deals("deals.txt");

            String topManager = findMostEffectiveManagerLastMonth(deals.getDeals());
            System.out.println("Most effective manager in last month: " + topManager);

            Map<String, Double> incomeByClients = collectIncomeByClients(deals.getDeals());
            StringBuilder clients = new StringBuilder();
            incomeByClients.forEach((k, v) -> clients.append(k).append(": ").append(v).append(", "));
            System.out.println("Income by clients:" + clients);

            Month mostProfitableMonth = findMostProfitableMonth(deals.getDeals()); // NPE
            if (mostProfitableMonth == null) {
                System.out.println("Most profitable month in last year not found");
            } else {
                System.out.println("Most profitable month in last year: " + mostProfitableMonth);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            System.out.println("Error, check application.log");
        }
    }
}