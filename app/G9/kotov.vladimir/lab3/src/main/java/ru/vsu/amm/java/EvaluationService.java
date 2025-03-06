package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EvaluationService {
    public static long countHelpfulEvaluations(Course course, List<Evaluation> evaluations) {
        return evaluations.stream()
                .filter(e -> e.helpful() && e.course() == course)
                .count();
    }
  public static Set<Course> coursesWithHighestScore(List<Evaluation> evaluations) {
        if (evaluations.isEmpty()) {
            return new HashSet<>();
        }
        int maxScore = evaluations.stream()
                .mapToInt(Evaluation::score)
                .max()
                .orElse(0);
        return evaluations.stream()
                .filter(e -> e.score() == maxScore)
                .map(Evaluation::course)
                .collect(Collectors.toSet());
    }

   public static long countNotHelpfulEvaluations(List<Evaluation> evaluations) {
        return evaluations.stream()
                .filter(e -> !e.helpful())
                .count();
    }
}
