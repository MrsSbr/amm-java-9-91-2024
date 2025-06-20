package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.enums.AchievementStatus;
import ru.vsu.amm.java.enums.AchievementType;
import ru.vsu.amm.java.repo.AchievementRepository;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;
import ru.vsu.amm.java.repo.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AchievementService {
    private final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private final UserRepository userRepository;
    private final EarnedAchievementRepository earnedAchievementRepository;
    private final AchievementRepository achievementRepository;

    public AchievementService(DataSource dataSource) {
        this.userRepository = new UserRepository(dataSource);
        this.earnedAchievementRepository = new EarnedAchievementRepository(dataSource);
        this.achievementRepository = new AchievementRepository(dataSource);
    }

    public List<EarnedAchievement> getAllAchievements(String login) throws SQLException {
        Long userId = userRepository.findByLogin(login).getId();
        return earnedAchievementRepository.findAllForUser(userId);
    }

    public Achievement getAchievementById(Long achievementId) throws SQLException {
        return achievementRepository.findById(achievementId).orElse(null);
    }


    public void addAchievementProgress(Long userId, Achievement achievement, int progress) throws SQLException {
        earnedAchievementRepository.addProgress(userId, achievement.getId(), progress);
        checkAndGrantAchievement(userId, achievement.getId());
    }

    public void checkAndGrantAchievement(Long userId, Long achievementId) throws SQLException {
        EarnedAchievement achievement = earnedAchievementRepository.findByUserIdAchievementId(userId, achievementId).get();
        if (achievement != null
                && achievement.getStatus() == AchievementStatus.LOCKED
                && achievement.getProgress() >= achievementRepository.findById(achievementId).get().getRequiredProgress()) {
            earnedAchievementRepository.unlockAchievement(userId, achievementId);
        }

    }

    public void handleRegistration(Long userId) throws SQLException {
        logger.log(Level.INFO, "Handling registration for user {0}", userId);
        Achievement achievement = achievementRepository.findByType(AchievementType.REGISTRATION)
                .orElseThrow();
        addAchievementProgress(userId, achievement, 1);
        logger.log(Level.INFO, "Successfully registered achievement");
    }

    public void handleLogin(Long userId) throws SQLException {
        logger.log(Level.INFO, "Handling login for user {0}", userId);
        Achievement achievement = achievementRepository.findByType(AchievementType.LOGIN_COUNT)
                .orElseThrow();
        addAchievementProgress(userId, achievement, 1);
        logger.log(Level.INFO, "Success login increment");
    }
}