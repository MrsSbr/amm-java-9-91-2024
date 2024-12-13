package ru.vsu.amm.java;


import static ru.vsu.amm.java.Constant.COUNT_STUDENT;

public class Application {
    public static void main(String[] args) {

        var reviews = new Reviews(COUNT_STUDENT);

        System.out.println(Service.numberUsefulReviews(Subjects.ALGEBRA, reviews.getListReview()));
        System.out.println(Service.subjectsWithMaxMark(reviews.getListReview()));
        System.out.println(Service.unusefulAllSubjects(reviews.getListReview()));
    }
}