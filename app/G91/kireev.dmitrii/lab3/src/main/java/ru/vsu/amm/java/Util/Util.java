package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Enum.Illness;
import ru.vsu.amm.java.Model.Patient;

import java.time.LocalDate;
import java.util.Random;


public class Util {


    public static Patient getRandomPatient() {

        Random random = new Random();

        String name = PatientConfig.NAMES.get(random.nextInt(PatientConfig.NAMES.size()));
        String surname = PatientConfig.SURNAMES.get(random.nextInt(PatientConfig.SURNAMES.size()));
        String patronymic = PatientConfig.PATRONYMICS.get(random.nextInt(PatientConfig.PATRONYMICS.size()));
        boolean isHealthy = random.nextBoolean();
        Illness illness = !isHealthy ? PatientConfig.ILLNESSES.get(random.nextInt(PatientConfig.ILLNESSES.size())) : Illness.HEALTHY;
        String individualNumber = getRandomIndividualNumber();

        return new Patient(individualNumber, name, surname, patronymic, isHealthy, illness, getRandomDate());
    }

    public static LocalDate getRandomDate() {

        Random random = new Random();

        LocalDate now = LocalDate.now();
        LocalDate firstDate = now.minusYears(10);
        int year = firstDate.getYear() + random.nextInt(now.getYear() - firstDate.getYear() + 1);
        int month = 1 + random.nextInt(now.getMonthValue());
        int day = 1 + random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());

        return LocalDate.of(year, month, day);
    }

    private static String getRandomIndividualNumber() {

        Random random = new Random();
        StringBuilder individualNumber = new StringBuilder();

        int groupSize = 3;
        int size = 11;
        int maxVal = 10;
        char separator = '-';

        for (int i = 0; i < size; i++) {
            if (i > 0 && i % 3 == 0) individualNumber.append(separator);
            individualNumber.append(random.nextInt(maxVal));
        }
        return individualNumber.toString();
    }
}
