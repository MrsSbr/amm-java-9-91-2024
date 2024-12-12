package ru.vsu.amm.java.classes.service;

import ru.vsu.amm.java.classes.entity.DataPerMonth;
import ru.vsu.amm.java.classes.enums.Gender;
import ru.vsu.amm.java.classes.entity.Student;

import java.util.List;
import java.util.ArrayList;
import java.time.Month;

public class Demographics {

    public static List<DataPerMonth> studPerMonth(List<Student> students) {
        List<DataPerMonth> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Month curMonth = Month.of(month);
            long maleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Male).count();
            long femaleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Female).count();
            result.add(new DataPerMonth(curMonth, (int) maleCnt, (int) femaleCnt));
        } return result;
    }

    public static List<Month> femaleMoreThenMale(List<Student> students) {
        List<Month> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Month curMonth = Month.of(month);
            long maleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Male).count();
            long femaleCnt = students.stream().filter(s -> s.getMonthOfBirth() == curMonth && s.getGender() == Gender.Female).count();
            if (maleCnt < femaleCnt) {
                months.add(curMonth);
            }
        }
        return months;
    }
}
