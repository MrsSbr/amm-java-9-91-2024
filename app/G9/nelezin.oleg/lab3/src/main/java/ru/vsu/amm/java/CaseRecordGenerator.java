package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.Random;

public class CaseRecordGenerator {

    private static final int days = 7300;

    private static final String[] names = {
            "Bill Gates",
            "Elon Mask",
            "Pavel Durov",
            "Steve Jobs",
            "Tim Cook",
            "Donald Trump",
            "Steve Wozniak",
            "Joe Biden"
    };

    private static final Article[] articles = {
            Article.MURDER,
            Article.FRAUD,
            Article.ROBBERY,
            Article.HOOLIGANISM,
            Article.THEFT
    };

    public static CaseRecord generateRecord() {
        Random random = new Random();
        int respondentIndex = random.nextInt(names.length);
        String respondent = names[respondentIndex];

        int plaintiffIndex;
        do {
            plaintiffIndex = random.nextInt(names.length);
        } while (respondentIndex == plaintiffIndex);
        String plaintiff = names[plaintiffIndex];

        LocalDate date = LocalDate.now().minusDays(random.nextInt(days));
        Article article = articles[random.nextInt(articles.length)];
        boolean isConvicted = random.nextBoolean();

        return CaseRecord.builder()
                .respondent(respondent)
                .plaintiff(plaintiff)
                .article(article)
                .date(date)
                .isConvicted(isConvicted)
                .build();
    }
}
