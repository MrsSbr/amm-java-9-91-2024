package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Config.PatientConfig;

import java.time.LocalDate;
import java.util.Random;

public class Util {

    private static final Random RANDOM = new Random();

    public static String getRandomString(int stringLength) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < stringLength; i++) {
            result.append((char) (RANDOM.nextInt(26) + 'a'));
        }

        return result.toString();
    }

    public static Boolean getRandomBoolean() {

        return RANDOM.nextBoolean();
    }

    public static LocalDate getRandomDate() {

        int year = PatientConfig.FIRSTDATE.getYear() + RANDOM.nextInt(PatientConfig.NOW.getYear() - PatientConfig.FIRSTDATE.getYear()+ 1);
        int month = 1 + RANDOM.nextInt(12);
        int day = 1 + RANDOM.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());

        return LocalDate.of(year, month,day);
    }
}
