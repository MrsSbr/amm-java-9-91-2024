import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.domain.entities.Player;
import ru.vsu.amm.java.domain.repository.PlayerRepository;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PlayerTests {
    private PlayerRepository playerRepository;
    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepository();
    }

    @AfterEach
    public void clean() throws SQLException {
        if (testPlayer != null) {
            playerRepository.delete(testPlayer);
        }
    }

    @Test
    public void playerRegistration() throws SQLException {
        String login = "testUser";
        String password = "testPassword";
        String email = "test@test.com";

        testPlayer = playerRepository.registratePlayer(email, login, password);

        assertNotNull(testPlayer.getId());
        assertNotNull(testPlayer.getLogin());
        assertNotNull(testPlayer.getEmail());
    }

    @Test public void playerLogin() throws SQLException {
        String login = "testUser";
        String password = "testPassword";
        String email = "test@test.com";

        testPlayer = playerRepository.registratePlayer(email, login, password);

        Player testPlayer2 = playerRepository.findByLogin(login);

        assertEquals(testPlayer, testPlayer2);
    }

}
