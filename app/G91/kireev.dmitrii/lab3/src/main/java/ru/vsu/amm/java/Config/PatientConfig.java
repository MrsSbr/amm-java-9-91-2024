package ru.vsu.amm.java.Config;

import ru.vsu.amm.java.Enum.Illness;
import java.util.List;

import static ru.vsu.amm.java.Enum.Illness.*;

public class PatientConfig {

    public static final int COUNT = 100;

    public static final List<String> NAMES = List.of("John", "Billy", "Bob", "Jully", "Ann", "Elizabeth", "Ron", "Bred", "Pit", "Aleck", "Fred");

    public static final List<String> SURNAMES = List.of("Brown", "White", "Wonka", "Holms", "Madder", "Jonson", "Stethem", "Aron", "Iron");

    public static final List<String> PATRONYMICS = List.of("Grace", "James", "Louise", "Sue", "Lois", "Santa", "Maria");

    public static final List<Illness> ILLNESSES = List.of(PNEUMONIA, ASTHMA, BRONCHITIS, EMPHYSEMA);

}

