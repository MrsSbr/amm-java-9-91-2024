package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reviews {

    private List<Review> listReview;
    private final static Random RANDOM = new Random();
    private final static Subjects[] SUBJECTS = Subjects.values();
    private final static Fio[] FIO = Fio.values();

    public Reviews(int count) {
        listReview = new ArrayList<>();
        generateReviews(count);
    }

    private void generateReviews(int count) {
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            int mark = random.nextInt(1, 6);
            Subjects subject = getRandomSubject();
            Fio FIO = getRandomFio();
            boolean isUseful = random.nextBoolean();
            listReview.add(new Review(mark, subject, FIO, isUseful));
        }
    }

    private static Subjects getRandomSubject() {
        return SUBJECTS[RANDOM.nextInt(SUBJECTS.length)];
    }

    private static Fio getRandomFio() {
        return FIO[RANDOM.nextInt(FIO.length)];
    }

    public List<Review> getListReview() {
        return listReview;
    }
}
