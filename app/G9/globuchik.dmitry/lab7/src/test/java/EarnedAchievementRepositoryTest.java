import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.enums.AchievementStatus;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EarnedAchievementRepositoryTest {

    private DataSource dataSource;
    private EarnedAchievementRepository earnedRepository;

    @BeforeEach
    void setup() throws SQLException, IOException {
        dataSource = DatabaseAccess.getDataSource();
        earnedRepository = new EarnedAchievementRepository(dataSource);
        initTestData();
    }

    private void initTestData() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS earnedachievement (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "achievement_id BIGINT, " +
                    "user_id BIGINT, " +
                    "obtainedat TIMESTAMP, " +
                    "status VARCHAR(20), " +
                    "progress INT)");

            stmt.execute("DELETE FROM earnedachievement");
            stmt.execute("INSERT INTO earnedachievement (achievement_id, user_id, status, progress) VALUES " +
                    "(1, 100, 'LOCKED', 0), " +
                    "(2, 100, 'LOCKED', 3)");
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE earnedachievement");
        }
    }

    @Test
    void testAddProgress() throws SQLException {
        earnedRepository.addProgress(100L, 2L, 2);

        Optional<EarnedAchievement> result = earnedRepository.findByUserIdAchievementId(100L, 2L);
        assertTrue(result.isPresent());
        assertEquals(5, result.get().getProgress());
    }

    @Test
    void testUnlockAchievement() throws SQLException {
        earnedRepository.unlockAchievement(100L, 1L);

        Optional<EarnedAchievement> result = earnedRepository.findByUserIdAchievementId(100L, 1L);
        assertTrue(result.isPresent());
        assertEquals(AchievementStatus.UNLOCKED, result.get().getStatus());
        assertNotNull(result.get().getObtainedAt());
    }
}