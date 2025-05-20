package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.repo.AchievementRepository;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;
import ru.vsu.amm.java.repo.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class AchievementService {
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
}