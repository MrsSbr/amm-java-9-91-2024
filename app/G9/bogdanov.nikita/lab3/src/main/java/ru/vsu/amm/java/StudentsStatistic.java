package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.entity.DataPerMonth;
import ru.vsu.amm.java.classes.service.Demographics;
import ru.vsu.amm.java.classes.utils.Generator;
import ru.vsu.amm.java.classes.entity.Student;

import java.time.Month;
import java.util.List;

public class StudentsStatistic {
    public static void main(String[] args) {
        List<Student> students = Generator.generateStud(1200);
        List<DataPerMonth> studBirthPerMonth = Demographics.studPerMonth(students);
        for (var stud : studBirthPerMonth) {
            System.out.printf("Month: %d; Male - %d; Female - %d%n", stud.getMonth(), stud.getMele(), stud.getFemale());
        }
        List<Month> monthWhenFemaleMore = Demographics.femaleMoreThenMale(students);
        System.out.printf("%nMonth, when female more birth then male - %s%n", monthWhenFemaleMore);
    }
}