package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Model.PrizeRecipient;
import ru.vsu.amm.java.Service.PrizeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PrizeGenerator {

    private static final List<String> participantNames = List.of(
            "Ava", "Ben", "Chloe", "Daniel", "Ella", "Finn", "Hazel", "Ian", "Joy", "Kai"
    );
    private static final List<String> teamNames = List.of(
            "DevOps", "QA", "UX", "Product", "Growth", "Data", "Security", "Ops"
    );
    private static final int START_YEAR = 2000;
    private static final int END_YEAR = 2030;


    private static PrizeRecipient createRandomRecipient() {
        Random generator = new Random();
        int year = generator.nextInt(END_YEAR - START_YEAR + 1) + START_YEAR;
        String participant = participantNames.get(generator.nextInt(participantNames.size()));
        String team = teamNames.get(generator.nextInt(teamNames.size()));

        return new PrizeRecipient(year, participant, team);
    }

    public static List<PrizeRecipient> generateRecipientList(int numberOfRecipients) {
        List<PrizeRecipient> data = new ArrayList<>();

        for (int i = 0; i < numberOfRecipients; i++) {
            data.add(createRandomRecipient());
        }

        return data;
    }
}