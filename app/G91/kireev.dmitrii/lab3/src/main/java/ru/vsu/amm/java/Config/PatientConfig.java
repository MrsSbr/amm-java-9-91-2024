package ru.vsu.amm.java.Config;

import ru.vsu.amm.java.Enum.Illness;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PatientConfig {

    public static final int COUNT = 100;

    public static final List<String> NAMES = List.of("John", "Billy", "Bob", "Jully", "Ann", "Elizabeth", "Ron", "Bred", "Pit", "Aleck", "Fred");

    public static final List<String> SURNAMES = List.of("Brown", "White", "Wonka", "Holms", "Madder", "Jonson", "Stethem", "Aron", "Iron");

    public static final List<String> PATRONYMICS = List.of("Grace", "James", "Louise", "Sue", "Lois", "Santa", "Maria");

    public static final List<Illness> ILLNESSES = Arrays.asList( Illness.values());

}

