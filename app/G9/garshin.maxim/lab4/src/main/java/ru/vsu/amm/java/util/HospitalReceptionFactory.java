package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.enums.Specialization;

import java.time.LocalDate;
import java.time.Year;
import java.util.Random;
import java.util.stream.IntStream;

public final class HospitalReceptionFactory {
    private final static Random random = new Random();

    public HospitalReceptionFactory() {
    }

    public static HospitalReception generateHospitalReception() {
        int year = random.nextInt(2020, LocalDate.now().getYear()+1);
        int day = random.nextInt(1, Year.isLeap(year) ? 367 : 366);
        LocalDate localDate = LocalDate.ofYearDay(year, day);

        var doctorFullname = IntStream.range(0, 3)
                .mapToObj(x -> (char)(random.nextInt(26) + 'a'))
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);

        Specialization[] SPECIALIZATIONS = Specialization.values();
        int specializationIndex = random.nextInt(SPECIALIZATIONS.length);
        Specialization specialization = SPECIALIZATIONS[specializationIndex];

        var patientFullname = IntStream.range(0, 3)
                .mapToObj(x -> (char)(random.nextInt(26) + 'a'))
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);

        int cost = random.nextInt(2500);

        return new HospitalReception(localDate, doctorFullname, specialization, patientFullname, cost);
    }
}
