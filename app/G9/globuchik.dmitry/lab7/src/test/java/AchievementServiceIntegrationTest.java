import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.enums.AchievementStatus;
import ru.vsu.amm.java.repo.AchievementRepository;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;
import ru.vsu.amm.java.services.AchievementService;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AchievementServiceIntegrationTest {

    private DataSource dataSource;
    private AchievementService achievementService;
    private EarnedAchievementRepository earnedRepository;
    private AchievementRepository achievementRepository;

    @BeforeEach
    void setup() throws SQLException, IOException {
        dataSource = DatabaseAccess.getDataSource();
        initDatabaseSchema();
        achievementService = new AchievementService(dataSource);
        earnedRepository = new EarnedAchievementRepository(dataSource);
        achievementRepository = new AchievementRepository(dataSource);
        insertTestData();
    }

    private void initDatabaseSchema() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS userentity (" +
                    "id              BIGSERIAL PRIMARY KEY,"
                    + "login           TEXT NOT NULL UNIQUE,"
                    + "nickname        TEXT NOT NULL,"
                    + "phonenumber     TEXT NOT NULL UNIQUE,"
                    + "passwordhash    TEXT NOT NULL,"
                    + "salt            TEXT NOT NULL,"
                    + "email           TEXT NOT NULL UNIQUE,"
                    + "login_count     INT NOT NULL DEFAULT 0)");

            stmt.execute("CREATE TABLE IF NOT EXISTS achievement (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "type VARCHAR(50), " +
                    "required_progress INT, " +
                    "description      TEXT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS earnedachievement (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "achievement_id BIGINT, " +
                    "user_id BIGINT, " +
                    "status VARCHAR(20), " +
                    "progress INT, " +
                    "obtainedat      TIME)");
        }
    }

    private void insertTestData() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            //INSERT INTO public.userentity (login, nickname, phonenumber, passwordhash, salt, email) VALUES (?, ?, ?, ?, ?, ?)
            stmt.execute("DELETE FROM userentity");
            stmt.execute("DELETE FROM achievement");
            stmt.execute("DELETE FROM earnedachievement");

            stmt.execute("INSERT INTO userentity (login, nickname, phonenumber, passwordhash, salt, email) VALUES (1, 'test_user', 8920, 'test_pass', 'test_salt', 'test_email')");
            stmt.execute("INSERT INTO achievement (id, name, type, required_progress, description) VALUES " +
                    "(10, 'Registration', 'REGISTRATION', 1, 'registration'), " +
                    "(20, 'Login Master', 'LOGIN_COUNT', 5, 'login')");
            stmt.execute("INSERT INTO earnedachievement (achievement_id, user_id, status, progress) VALUES " +
                    "(10, 1, 'LOCKED', 0), " +
                    "(20, 1, 'LOCKED', 0)");
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS userentity");
            stmt.execute("DROP TABLE IF EXISTS achievement");
            stmt.execute("DROP TABLE IF EXISTS earnedachievement");
        }
    }

    @Test
    void testHandleRegistration() throws SQLException {
        achievementService.handleRegistration(1L);

        Optional<EarnedAchievement> result = earnedRepository.findByUserIdAchievementId(1L, 10L);
        assertTrue(result.isPresent());
        assertEquals(AchievementStatus.UNLOCKED, result.get().getStatus());
        assertEquals(1, result.get().getProgress());

        Optional<EarnedAchievement> loginAchievement = earnedRepository.findByUserIdAchievementId(1L, 20L);
        assertTrue(loginAchievement.isPresent());
        assertEquals(0, loginAchievement.get().getProgress());
    }

    @Test
    void testCheckAndGrantAchievement() throws SQLException {
        earnedRepository.addProgress(1L, 20L, 5);

        Achievement achievement = achievementRepository.findById(20L).orElseThrow();

        achievementService.checkAndGrantAchievement(1L, 20L);

        Optional<EarnedAchievement> result = earnedRepository.findByUserIdAchievementId(1L, 20L);
        assertTrue(result.isPresent());
        assertEquals(AchievementStatus.UNLOCKED, result.get().getStatus());
        assertEquals(5, result.get().getProgress());
    }
}