package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Reviews {

    private List<Review> listReview;
    private final static Random RANDOM = new Random();
    private final static Subjects[] SUBJECTS = Subjects.values();

    public Reviews(int count) {
        listReview = new ArrayList<>();
        generateReviews(count);
    }

    private void generateReviews(int count) {
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            int mark = random.nextInt(1, 6);
            Subjects subject = getRandomSubject();
            String FIO = getRandomFio();
            boolean isUseful = random.nextBoolean();
            listReview.add(new Review(mark, subject, FIO, isUseful));
        }
    }

    private static Subjects getRandomSubject() {
        return SUBJECTS[RANDOM.nextInt(SUBJECTS.length)];
    }

    private static String getRandomFio() {
        return IntStream.range(0, 3)
                .mapToObj(x -> (char)(RANDOM.nextInt(26) + 'a'))
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);
    }

    public List<Review> getListReview() {
        return listReview;
    }
}
