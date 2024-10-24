package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Model.PatientDTO;

import java.time.LocalDate;
import java.util.Random;

import static ru.vsu.amm.java.Config.PatientConfig.patronymics;

public class Util {


    public static PatientDTO getRandomPatient() {

        Random random = new Random();

        String name = PatientConfig.names.get(random.nextInt(PatientConfig.names.size()));
        String surname = PatientConfig.surnames.get(random.nextInt(PatientConfig.surnames.size()));
        String patronymic = patronymics.get(random.nextInt(patronymics.size()));
        boolean isHealthy = random.nextBoolean();
        String illness = !isHealthy ? PatientConfig.illnesses.get(random.nextInt(PatientConfig.illnesses.size())) : "Healthy";

        return new PatientDTO(name, surname, patronymic, isHealthy, illness, getRandomDate());


    }

    public static LocalDate getRandomDate() {

        Random random = new Random();

        int year = PatientConfig.FIRST_DATE.getYear() + random.nextInt(PatientConfig.NOW.getYear() - PatientConfig.FIRST_DATE.getYear() + 1);
        int month = 1 + random.nextInt(PatientConfig.NOW.getMonthValue());
        int day = 1 + random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth());

        return LocalDate.of(year, month, day);
    }
}
