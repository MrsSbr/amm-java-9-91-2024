package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.service.Demographics;
import ru.vsu.amm.java.classes.utils.Generator;
import ru.vsu.amm.java.classes.entity.Student;
import java.util.List;



public class StudentsStatistic {
    public static void main(String[] args) {
        List<Student> students = Generator.generateStud(1200);
        List<int[]> studBirthPerMonth = Demographics.studPerMonth(students);
        for (int[] stud : studBirthPerMonth) {
            System.out.printf("Month: %d; Male - %d; Female - %d%n", stud[0], stud[1], stud[2]);
        }
        List<Integer> monthWhenFemaleMore = Demographics.femaleMoreThenMale(students);
        System.out.printf("%nMonth, when female more birth then male - %s%n", monthWhenFemaleMore);
    }
}