package ru.vsu.amm.java;

import ru.vsu.amm.java.random.PrizeGenerator;
import ru.vsu.amm.java.winners.PrizeStorage;
import ru.vsu.amm.java.winners.PrizeRecipient;

public class Main {
    public static void main(String[] args) {
        PrizeStorage<PrizeRecipient> storage = new PrizeStorage<>();

        PrizeGenerator.populatePrizeStorage(storage, 100);
        storage.printRecipients();
    }
}