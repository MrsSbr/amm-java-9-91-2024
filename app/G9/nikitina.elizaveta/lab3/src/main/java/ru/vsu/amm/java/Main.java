package ru.vsu.amm.java;

import ru.vsu.amm.java.random.PrizeGenerator;
import ru.vsu.amm.java.winners.PrizeStorage;
import ru.vsu.amm.java.winners.PrizeRecipient;

public class Main {
    public static void main(String[] args) {
        PrizeStorage<PrizeRecipient> storage = new PrizeStorage<>();

        PrizeGenerator.populatePrizeStorage(storage, 100);
        storage.printRecipients();

        // 1. Найти отдел (отделы), работники которого выигрывали чаще всего
        System.out.println("Отделы, которые выигрывали чаще всего:");
        storage.printMostRecurringGroup();

        // 2. Список работников, которые выигрывали премию
        System.out.println("\nРаботники, которые выигрывали премию:");
        storage.printRecipients();

        // 3. Узнать количество работников, которые становились лучшими, только 1 раз
        System.out.println("\nКоличество работников, которые выигрывали только 1 раз:");
        storage.printUniqueRecipientCount();
    }
}
