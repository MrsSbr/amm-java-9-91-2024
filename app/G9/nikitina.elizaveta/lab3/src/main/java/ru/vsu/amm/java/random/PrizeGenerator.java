package ru.vsu.amm.java.random;

import ru.vsu.amm.java.winners.PrizeRecipient;
import ru.vsu.amm.java.winners.PrizeStorage;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PrizeGenerator {
    private static final Random generator = new Random();
    private static final List<String> participantNames = List.of(
            "Ava", "Ben", "Chloe", "Daniel", "Ella", "Finn", "Hazel", "Ian", "Joy", "Kai"
    );
    private static final List<String> teamNames = List.of(
            "DevOps", "QA", "UX", "Product", "Growth", "Data", "Security", "Ops"
    );
    private static final int START_CYCLE = 2000;
    private static final int END_CYCLE = 2030;


    public static PrizeRecipient createRandomRecipient() {
        int cycle = generator.nextInt(END_CYCLE - START_CYCLE + 1) + START_CYCLE;
        String participant = participantNames.get(generator.nextInt(participantNames.size()));
        String team = teamNames.get(generator.nextInt(teamNames.size()));

        return new PrizeRecipient(cycle, participant, team);
    }

    public static void populatePrizeStorage(PrizeStorage<PrizeRecipient> storage, int numberOfRecipients) {
        IntStream.range(0, numberOfRecipients)
                .mapToObj(i -> createRandomRecipient())
                .forEach(storage::add);
    }
}