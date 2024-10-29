package ru.vsu.amm.java;

import ru.vsu.amm.java.staff.Barista;

public class Main {
    public static void main(String[] args) {
        Barista barista = new Barista();
        barista.generateRandomDrinkRecords();
        System.out.println(barista.getDrinksNotOrderedLast3Months());
    }
}