package ru.vsu.amm.java.util;

import ru.vsu.amm.java.battlerecord.BattleRecord;
import ru.vsu.amm.java.enums.Animal;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleRecordGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] NAMES = {"Spartacus", "Crixus", "Alexus", "Abobus"};
    private static final String[] LUDUS_NAMES = {"Magnus", "", "Capua", "", "Zabor"};

    public static List<BattleRecord> generateBattleRecords(int count) {
        List<BattleRecord> battleRecords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            battleRecords.add(generateBattleRecord());
        }
        return battleRecords;
    }

    private static BattleRecord generateBattleRecord() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        String ludus = LUDUS_NAMES[RANDOM.nextInt(LUDUS_NAMES.length)];
        LocalDate date = getRandomDate();
        Animal animal = Animal.values()[RANDOM.nextInt(Animal.values().length)];
        boolean gladiatorWon = RANDOM.nextBoolean();
        boolean gladiatorPardoned = RANDOM.nextBoolean();
        return new BattleRecord(date, name, ludus, animal, gladiatorWon, gladiatorPardoned);
    }

    private static LocalDate getRandomDate() {
        int year = 105 + RANDOM.nextInt(300);
        int month = 1 + RANDOM.nextInt(12);
        int day = 1 + RANDOM.nextInt(YearMonth.of(year, month).lengthOfMonth());
        return LocalDate.of(year, month, day);
    }

}
