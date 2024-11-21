package ru.vsu.amm.java;

import ru.vsu.amm.java.service.DrinkRecordAnalyzer;
import ru.vsu.amm.java.staff.BaristaStorage;

public class Main {
    public static void main(String[] args) {
        BaristaStorage baristaStorage = new BaristaStorage();
        baristaStorage.generateRandomDrinkRecords();

        DrinkRecordAnalyzer analyzer = new DrinkRecordAnalyzer(baristaStorage);
        System.out.println(analyzer.getDrinksNotOrderedLast3Months());

    }
}