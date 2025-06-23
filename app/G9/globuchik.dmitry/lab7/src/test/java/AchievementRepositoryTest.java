import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.enums.AchievementType;
import ru.vsu.amm.java.repo.AchievementRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AchievementRepositoryTest {

    private DataSource dataSource;
    private AchievementRepository achievementRepository;
    private Long registrationAchievementId;


    @BeforeEach
    void setup() throws SQLException, IOException {
        dataSource = DatabaseAccess.getDataSource();
        achievementRepository = new AchievementRepository(dataSource);
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS achievement (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "name TEXT, " +
                    "description TEXT, " +
                    "type TEXT, " +
                    "required_progress INT)");

            stmt.execute("TRUNCATE TABLE achievement CASCADE");
            stmt.executeUpdate("INSERT INTO achievement (name, description, type, required_progress) VALUES " +
                    "('Login', 'Login 5 times', 'LOGIN_COUNT', 5)");

            stmt.executeUpdate(
                    "INSERT INTO achievement (name, description, type, required_progress) " +
                            "VALUES ('Registration', 'Complete registration', 'REGISTRATION', 1)",
                    Statement.RETURN_GENERATED_KEYS);

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    registrationAchievementId = rs.getLong(1);
                }
            }
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE achievement CASCADE");
        }
    }

    @Test
    void testFindByType() throws SQLException {
        Optional<Achievement> achievement = achievementRepository.findByType(AchievementType.REGISTRATION);

        assertTrue(achievement.isPresent());
        assertEquals("Registration", achievement.get().getName());
        assertEquals(AchievementType.REGISTRATION, achievement.get().getType());
        assertEquals(1, achievement.get().getRequiredProgress());
    }

    @Test
    void testFindById() throws SQLException {
        Optional<Achievement> achievement = achievementRepository.findById(registrationAchievementId);

        assertTrue(achievement.isPresent());
        assertEquals("Registration", achievement.get().getName());
        assertEquals(AchievementType.REGISTRATION, achievement.get().getType());
    }
}