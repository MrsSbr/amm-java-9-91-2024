package ru.vsu.amm.java.classes.utils;

import ru.vsu.amm.java.classes.entity.Student;
import ru.vsu.amm.java.classes.enums.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Month;

public class Generator {
    private static final String[] NAMESFORMALE = {"Igor'", "Ivan", "Sergay", "Nikita", "Oleg"};

    private static final String[] NAMESFORFEMALE = {"Lera", "Liza", "Ira", "Sveta", "Vika"};

    public static List<Student> generateStud(int count) {
        Random rand = new Random();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name;
            Gender gender = rand.nextInt(2) % 2 == 0 ? Gender.Male : Gender.Female;
            if (gender == Gender.Female) {
                int randIndex = rand.nextInt(NAMESFORMALE.length);
                name = NAMESFORFEMALE[randIndex];
            } else {
                int randIndex = rand.nextInt(NAMESFORFEMALE.length);
                name = NAMESFORMALE[randIndex];
            }
            Month month = Month.of(rand.nextInt(12) + 1);
            students.add(new Student(name, gender, month));
        }
        return students;
    }
}
