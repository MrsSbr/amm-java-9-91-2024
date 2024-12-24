package ru.vsu.amm.java;

import java.util.List;

public class FeedingApplication {
    private static final String PATH = "C:\\Users\\Tanya\\Desktop\\JavaLab2\\app\\G91\\kurkina.tanya\\lab4\\src\\main\\java\\ru\\vsu\\amm\\java\\data\\data";
    public static void main(String[] args) {
        List<FeedingRecord> records = FeedingFileHandler.readFeedingRecords(PATH);

        System.out.println("Animal with most food last month: " +
                FeedingService.findAnimalWithMostFoodLastMonth(records));

        System.out.println("Month with most variety: " +
                FeedingService.findMonthWithMostVariety(records));

        System.out.println("Products not repeated: " +
                FeedingService.findProductsNotRepeated(records));
    }
}