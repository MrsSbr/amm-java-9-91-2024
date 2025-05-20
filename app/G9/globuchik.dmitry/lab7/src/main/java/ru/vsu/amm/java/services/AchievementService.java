package ru.vsu.amm.java.services;

import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.repo.AchievementRepository;
import ru.vsu.amm.java.repo.EarnedAchievementRepository;
import ru.vsu.amm.java.repo.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AchievementService {

    public AchievementService() {
    }

    public List<EarnedAchievement> getAllAchievements(String login) throws SQLException, IOException {
        final UserRepository userRepository = new UserRepository(DatabaseAccess.getDataSource());
        final EarnedAchievementRepository earnedAchievementRepository = new EarnedAchievementRepository(DatabaseAccess.getDataSource());

        Long userId = userRepository.findByLogin(login).getId();
        return earnedAchievementRepository.findAllForUser(userId);
    }

    public Achievement getAchievementById(Long achievementId) throws IOException, SQLException {
        final AchievementRepository repository = new AchievementRepository(DatabaseAccess.getDataSource());
        return repository.findById(achievementId).isPresent() ? repository.findById(achievementId).get() : null;
    }
}
