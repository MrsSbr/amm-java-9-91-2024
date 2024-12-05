package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Service {

    public static Long numberUsefulReviews(Subjects subject, List<Review> reviewList) {
        return reviewList.stream()
                .filter(review -> review.isUseful() && review.subject() == subject).count();
    }

    public static Set<Subjects> subjectsWithMaxMark(List<Review> reviewList) {
        if (reviewList.isEmpty()) {
            return new HashSet<>();
        }

        int maxMark = reviewList.stream()
                .mapToInt(Review::mark).max().getAsInt();

        return reviewList.stream()
                .filter(review -> review.mark() == maxMark)
                .map(Review::subject).collect(Collectors.toSet());

    }

    public static Long unusefulAllSubjects(List<Review> reviewList) {
        return reviewList.stream()
                .filter(review -> !review.isUseful())
                .count();
    }
}
