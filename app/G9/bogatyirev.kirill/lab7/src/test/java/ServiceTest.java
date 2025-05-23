import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.data.service.PasswordEncodeService;
import ru.vsu.amm.java.data.service.WordToActionService;
import ru.vsu.amm.java.data.service.WordsService;
import ru.vsu.amm.java.domain.entities.WordToAction;
import ru.vsu.amm.java.domain.repository.WordToActionRepository;


import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class ServiceTest {
    private PasswordEncodeService passwordEncodeService;
    private WordsService wordsService;
    private WordToActionService wordToActionService;
    private WordToActionRepository wordToActionRepository;


    @BeforeEach
    void setUp() {
        passwordEncodeService = new PasswordEncodeService();
        wordsService = new WordsService();
        wordToActionService = new WordToActionService();
        wordToActionRepository = new WordToActionRepository();
    }

    @Test public void passwordEncodeTest() {
        String password = "password";
        String encodedPassword = passwordEncodeService.encode(password);

        assertTrue(passwordEncodeService.checkPassword(password, encodedPassword));

    }

    @Test public void wordsServiceTest() {
        List<String> words = wordsService.getRandomWords(30);
        assertTrue(words.size() >= 30);
    }

    @Test public void wordToActionServiceTest() throws SQLException {
        int count = 6;
        Long cardId = 1L;
        List<WordToAction> wordToActions = wordToActionService.generateWordToActions(count, cardId);

        assertTrue(wordToActions.size() == count);


        for (WordToAction wta : wordToActions) {
            if (wta.getId() != null) {
                wordToActionRepository.delete(wta);
            }
        }

    }
}
