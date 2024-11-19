package ru.vsu.amm.java;

import ru.vsu.amm.java.generate.RandomWinnerGenerator;
import ru.vsu.amm.java.winners.StorageWinners;
import ru.vsu.amm.java.winners.Winner;

public class Main {
    public static void main(String[] args) {
        StorageWinners<Winner> storageWinners = new StorageWinners<>();

        RandomWinnerGenerator.populateStorageWinners(storageWinners, 100); // Генерация 100 победителей
        storageWinners.printWinners();
    }
}