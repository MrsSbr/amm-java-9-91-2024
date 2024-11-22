package ru.vsu.amm.java;

import ru.vsu.amm.java.logger.LoggerConfig;
import ru.vsu.amm.java.services.Deals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Month;
import java.util.Map;
import java.util.logging.Logger;

import static ru.vsu.amm.java.services.DealsAnalyzer.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, URISyntaxException {
        LoggerConfig.configure();
        Deals deals = new Deals("deals.txt");

        String topManager = findMostEffectiveManagerLastMonth(deals.getDeals());
        LOGGER.info("Most effective manager in last month: " + topManager);

        Map<String, Double> incomeByClients = collectIncomeByClients(deals.getDeals());
        StringBuilder clients = new StringBuilder();
        incomeByClients.forEach((k, v) -> clients.append(k).append(": ").append(v).append(", "));

        LOGGER.info("Income by clients:" + clients);

        Month mostProfitableMonth = findMostProfitableMonth(deals.getDeals());
        LOGGER.info("Most profitable month in last year: " + mostProfitableMonth);
    }
}