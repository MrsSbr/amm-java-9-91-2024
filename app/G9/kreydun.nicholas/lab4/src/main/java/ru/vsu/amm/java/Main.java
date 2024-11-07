package ru.vsu.amm.java;

import ru.vsu.amm.java.staff.BaristaStorage;

public class Main {
    public static void main(String[] args) {
        BaristaStorage barista = new BaristaStorage();
        barista.generateRandomDrinkRecords();
        System.out.println(barista.getDrinksNotOrderedLast3Months());
    }
}