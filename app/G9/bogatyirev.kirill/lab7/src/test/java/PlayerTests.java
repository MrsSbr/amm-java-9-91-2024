import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.data.service.AuthService;
import ru.vsu.amm.java.domain.entities.Player;
import ru.vsu.amm.java.domain.repository.PlayerRepository;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PlayerTests {
    private PlayerRepository playerRepository;
    private Player testPlayer;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepository();
        authService = new AuthService();
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

    @Test public void authServiceLogin() {
        String login = "testUser";
        String password = "testPassword";
        String email = "test@test.com";


        testPlayer = authService.registration(email, login, password);
        testPlayer = authService.login(email, login);

        assertNotNull(testPlayer.getId());
    }

}
