package ru.vsu.amm.java.util;

import ru.vsu.amm.java.enums.Article;
import ru.vsu.amm.java.entity.CaseRecord;

import java.time.LocalDate;
import java.util.Random;

public class CaseRecordFactory {

    private static final int DAYS = 7300;

    private static final String[] NAMES = {
            "Bill Gates",
            "Elon Mask",
            "Pavel Durov",
            "Steve Jobs",
            "Tim Cook",
            "Donald Trump",
            "Steve Wozniak",
            "Joe Biden"
    };

    private static final Article[] ARTICLES = {
            Article.MURDER,
            Article.FRAUD,
            Article.ROBBERY,
            Article.HOOLIGANISM,
            Article.THEFT
    };

    public static CaseRecord generateRecord() {
        Random random = new Random();
        int respondentIndex = random.nextInt(NAMES.length);
        String respondent = NAMES[respondentIndex];

        int plaintiffIndex;
        do {
            plaintiffIndex = random.nextInt(NAMES.length);
        } while (respondentIndex == plaintiffIndex);
        String plaintiff = NAMES[plaintiffIndex];

        LocalDate date = LocalDate.now().minusDays(random.nextInt(DAYS));
        Article article = ARTICLES[random.nextInt(ARTICLES.length)];
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
