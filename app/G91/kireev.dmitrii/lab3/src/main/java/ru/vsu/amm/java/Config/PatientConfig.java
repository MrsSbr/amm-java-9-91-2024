package ru.vsu.amm.java.Config;

import java.time.LocalDate;
import java.util.List;

public class PatientConfig {

    public static final int COUNT = 1964;

    public static final LocalDate NOW = LocalDate.now();

    public static final LocalDate FIRST_DATE = NOW.minusYears(10);

    public static final LocalDate HEALTHY_PERIOD = NOW.minusYears(1);

    public static final LocalDate TASK2_PERIOD = NOW.minusYears(3);

    public static final LocalDate TASK3_AFTER = NOW.minusYears(5);

    public static final LocalDate TASK3_BEFORE = NOW.minusYears(2);

    public static final List<String> names = List.of("John", "Billy", "Bob", "Jully", "Ann", "Elizabeth", "Ron", "Bred", "Pit", "Aleck", "Fred");

    public static final List<String> surnames = List.of("Brown", "White", "Wonka", "Holms", "Madder", "Jonson", "Stethem", "Aron", "Iron");

    public static final List<String> patronymics = List.of("Grace", "James", "Louise", "Sue", "Lois", "Santa", "Maria");

    public static final List<String> illnesses = List.of("Pneumonia", "Asthma", "Bronchitis", "Emphysema");

}

