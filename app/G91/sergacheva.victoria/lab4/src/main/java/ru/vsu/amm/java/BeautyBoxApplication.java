package ru.vsu.amm.java;

import ru.vsu.amm.java.service.BeautyBoxService;
import ru.vsu.amm.java.service.FileService;

import java.util.Map;

public class BeautyBoxApplication {

    private static final String PATH = "app/G91/sergacheva.victoria/lab4/src/main/java/ru/vsu/amm/java/data";

    public static void main(String[] args) {
        FileService fileService = new FileService();
        var beautyBoxes = fileService.readBeautyBoxData(PATH);

        BeautyBoxService beautyBoxService = new BeautyBoxService();

        System.out.println("Product frequency: ");
        Map<String, Long> productFrequency = beautyBoxService.calculateProductFrequency(beautyBoxes);
        productFrequency.forEach((key, value) ->
                System.out.println("Product: " + key + ", Count: " + value));


        String bestMonth = beautyBoxService.findBestSalesMonth(beautyBoxes);
        System.out.println("Best month: " + bestMonth + "\n");

        System.out.println("First appearance:");
        Map<String, String> firstAppearance = beautyBoxService.findFirstAppearanceByMonth(beautyBoxes);
        firstAppearance.forEach((key, value) ->
                System.out.println("Product: " + key + ", First Appeared in: " + value));
    }
}