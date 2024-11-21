package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Service {

    public static int numberUsefulReviews(Subjects subject, List<Review> reviewList) {
        return (int) reviewList.stream()
                .filter(review -> review.isUseful() && review.subject() == subject).count();
    }

    public static Set<Subjects> subjectsWithMaxMark(List<Review> reviewList) {
        Set<Subjects> subjectMaxMark = new HashSet<>();
        int maxMark = 0;
        for (Review review: reviewList) {
            if (review.mark() > maxMark) {
                maxMark = review.mark();
                subjectMaxMark = new HashSet<>();
                subjectMaxMark.add(review.subject());
            } else if (review.mark() == maxMark) {
                subjectMaxMark.add(review.subject());
            }
        }
        return subjectMaxMark;

    }

    public static int unusefulAllSubjects(List<Review> reviewList) {
        return (int) reviewList.stream()
                .filter(review -> !review.isUseful())
                .count();
    }
}
