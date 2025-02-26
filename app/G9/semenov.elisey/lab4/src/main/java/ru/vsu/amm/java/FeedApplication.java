package ru.vsu.amm.java;


import ru.vsu.amm.java.entity.FeedRecord;
import ru.vsu.amm.java.service.FeedService;
import ru.vsu.amm.java.service.FileService;

import java.util.List;

public class FeedApplication {
    private static final String PATH = "app/G9/semenov.elisey/lab4/src/main/java/ru/vsu/amm/java/feeds.txt";

    public static void main(String[] args) {
        List<FeedRecord> records = FileService.readFeedRecords(PATH);

        FeedService feedService = new FeedService();
        System.out.println(feedService.findMostEatingAnimal(records));

        System.out.println(feedService.findMostDiverseProductsMonth(records));

        System.out.println(FeedService.findMissingProducts(records));
    }
}