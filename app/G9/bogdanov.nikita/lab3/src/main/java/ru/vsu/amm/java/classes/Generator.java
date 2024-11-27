package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private static final String[] NAMESFORMALE = {
            "Igor'",
            "Ivan",
            "Sergay",
            "Nikita",
            "Oleg"
    };

    private static final String[] NAMESFORFEMALE = {
            "Lera",
            "Liza",
            "Ira",
            "Sveta",
            "Vika"
    };

    public static List<Student> generateStud (int count) {
        Random rand = new Random();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name;
            Gender gender = rand.nextInt(2) % 2 == 0 ? Gender.Male : Gender.Female;
            if (gender == Gender.Female) {
                int randIndex = rand.nextInt(NAMESFORMALE.length);
                name = NAMESFORFEMALE[randIndex];
            } else
            {
                int randIndex = rand.nextInt(NAMESFORFEMALE.length);
                name = NAMESFORMALE[randIndex];
            }
            int month = rand.nextInt(12) + 1;
            students.add(new Student(name,gender, month));
        }
        return students;
    }
}
