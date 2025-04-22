
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.data.service.CardService;
import ru.vsu.amm.java.domain.entities.Card;
import ru.vsu.amm.java.domain.repository.CardRepository;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;



public class GameProcessTests {
    private CardService cardService;
    private CardRepository cardRepository;
    private Card testCard;

    @BeforeEach
    public void setUp() {
        cardService = new CardService();
        cardRepository = new CardRepository();
    }

    @AfterEach
    public void clean() throws SQLException {
        if(testCard != null) {
            cardRepository.delete(testCard);
        }
    }

    @Test
    public void generateCardTest() {
        Long playerId = 1L;
        testCard = cardService.generateCard(playerId);

        assertNotNull(testCard.getId());
        assertNotNull(testCard.getTopic());
        assertNotNull(testCard.getWordsToActions());

        assertEquals(testCard.getWordsToActions().size(), 6);
    }

    @Test
    public void rollDiceTest() {
        int result = cardService.rollDice();
        assertTrue(result > 0 && result < 7);
    }
}
