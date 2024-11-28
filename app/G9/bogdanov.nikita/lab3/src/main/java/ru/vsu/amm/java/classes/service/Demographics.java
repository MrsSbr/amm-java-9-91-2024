package ru.vsu.amm.java.classes.service;

import ru.vsu.amm.java.classes.enums.Gender;
import ru.vsu.amm.java.classes.entity.Student;

import java.util.List;
import java.util.ArrayList;

public class Demographics {

    public static List<int[]> studPerMonth (List<Student> students) {
        List<int[]> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            int curMonth = month;
            long maleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Male)
                    .count();
            long femaleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Female)
                    .count();
            result.add(new int[]{curMonth, (int) maleCnt, (int) femaleCnt});
        }
        return result;
    }

    public static List<Integer> femaleMoreThenMale (List<Student> students) {
        List<Integer> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            int curMonth = month;
            long maleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Male)
                    .count();
            long femaleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Female)
                    .count();
            if (maleCnt < femaleCnt) {
                months.add(curMonth);
            }
        }
        return months;
    }
}
