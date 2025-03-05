package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Survey {
    private List<Evaluation> evaluations;
    private static final Random RANDOM = new Random();
    private static final Course[] ALL_COURSES = Course.values();

    public Survey(int totalStudents) {
        evaluations = new ArrayList<>();
        generateEvaluations(totalStudents);
    }

    private void generateEvaluations(int totalStudents) {
         for (int i = 0; i < totalStudents; i++) {
            int score = RANDOM.nextInt(1, 6); // оценка от 1 до 5
            Course course = randomCourse();
            String studentName = randomStudentName();
            boolean helpful = RANDOM.nextBoolean();
            evaluations.add(new Evaluation(score, course, studentName, helpful));
        }
    }

    private static Course randomCourse() {
        return ALL_COURSES[RANDOM.nextInt(ALL_COURSES.length)];
    }

    private static String randomStudentName() {
         return IntStream.range(0, 3)
                .mapToObj(i -> String.valueOf((char)(RANDOM.nextInt(26) + 'a')))
                .reduce("", String::concat);
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
}
