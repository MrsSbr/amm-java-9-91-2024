import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Article;
import ru.vsu.amm.java.CaseRecord;
import ru.vsu.amm.java.CaseRecordService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CaseRecordServiceTest {

    private CaseRecordService caseRecordService;

    @BeforeEach
    public void setup() {
        caseRecordService = new CaseRecordService();
    }

    @Test
    public void testGetNotConvictedPeople() {
        List<CaseRecord> records = getSomeRecords();
        Set<String> notConvicted = caseRecordService.getNotConvictedPeople(records);

        assertNotNull(notConvicted);
        assertTrue(notConvicted.contains("Ivan"));
        assertTrue(notConvicted.contains("Oleg"));
        assertFalse(notConvicted.contains("Sergey"));
    }

    @Test
    public void testGetPeopleMultipleArticles() {
        List<CaseRecord> records = getSomeRecords();
        Set<String> multipleArticles = caseRecordService.getPeopleMultipleArticles(records);

        assertNotNull(multipleArticles);
        assertTrue(multipleArticles.contains("Ivan"));
        assertFalse(multipleArticles.contains("Oleg"));
    }

    @Test
    public void testGetFrequentPlaintiffs() {
        List<CaseRecord> records = getSomeRecords();
        Set<String> frequentPlaintiffs = caseRecordService.getFrequentPlaintiffs(records);

        assertNotNull(frequentPlaintiffs);
        assertTrue(frequentPlaintiffs.contains("Maria"));
        assertFalse(frequentPlaintiffs.contains("Anna"));
    }

    private static List<CaseRecord> getSomeRecords() {
        return Arrays.asList(
                CaseRecord.builder()
                        .plaintiff("Ivan")
                        .respondent("Maria")
                        .date(LocalDate.now().minusYears(2))
                        .article(Article.MURDER)
                        .isConvicted(false)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Ivan")
                        .respondent("Oleg")
                        .date(LocalDate.now().minusYears(1))
                        .article(Article.FRAUD)
                        .isConvicted(false)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Sergey")
                        .respondent("Anna")
                        .date(LocalDate.now().minusYears(4))
                        .article(Article.ROBBERY)
                        .isConvicted(true)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Maria")
                        .respondent("Ivan")
                        .date(LocalDate.now().minusYears(2))
                        .article(Article.MURDER)
                        .isConvicted(false)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Anna")
                        .respondent("Alexey")
                        .date(LocalDate.now().minusYears(3))
                        .article(Article.MURDER)
                        .isConvicted(true)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Oleg")
                        .respondent("Sergey")
                        .date(LocalDate.now().minusYears(1))
                        .article(Article.HOOLIGANISM)
                        .isConvicted(true)
                        .build(),
                CaseRecord.builder()
                        .plaintiff("Maria")
                        .respondent("Ivan")
                        .date(LocalDate.now().minusMonths(2))
                        .article(Article.FRAUD)
                        .isConvicted(false)
                        .build()
                        );
    }
}
