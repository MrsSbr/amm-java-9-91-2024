package ru.vsu.amm.java;

import static ru.vsu.amm.java.classes.Demographics.*;
import ru.vsu.amm.java.classes.Student;
import static ru.vsu.amm.java.classes.Generator.*;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        List<Student> students = generateStud(1200);
        List<int[]> studBirthPerMonth = studPerMonth(students);
        for (int[] stud : studBirthPerMonth) {
            System.out.printf("Month: %d; Male - %d; Female - %d%n", stud[0], stud[1], stud[2]);
        }
        List<Integer> monthWhenFemaleMore = femaleMoreThenMale(students);
        System.out.printf("%nMonth, when female more birth then male - %s%n", monthWhenFemaleMore);
    }
}