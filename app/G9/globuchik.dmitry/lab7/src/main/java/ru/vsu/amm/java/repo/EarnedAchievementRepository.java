package ru.vsu.amm.java.repo;

import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.enums.AchievementStatus;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EarnedAchievementRepository implements Repository<EarnedAchievement> {
    private final DataSource dataSource;
    Logger logger = Logger.getLogger(EarnedAchievementRepository.class.getName());

    public EarnedAchievementRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<EarnedAchievement> findById(final Long id) throws SQLException {
        final String sql = "SELECT id, achievement_id, user_id, obtainedat, status, progress FROM earnedachievement WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new EarnedAchievement(
                            resultSet.getLong("id"),
                            resultSet.getLong("achievement_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getTimestamp("obtainedat").toLocalDateTime(),
                            AchievementStatus.valueOf(resultSet.getString("status")),
                            resultSet.getInt("progress")));
                }
            }
            return Optional.empty();
        }
    }


    @Override
    public List<EarnedAchievement> findAll() throws SQLException {
        final String sql = "SELECT id, achievement_id, user_id, obtainedat, status, progress FROM earnedachievement ORDER BY achievement_id";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<EarnedAchievement> achievements = new ArrayList<>();

                while (resultSet.next()) {
                    achievements.add(new EarnedAchievement(
                            resultSet.getLong("id"),
                            resultSet.getLong("achievement_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getTimestamp("obtainedat").toLocalDateTime(),
                            AchievementStatus.valueOf(resultSet.getString("status")),
                            resultSet.getInt("progress")));
                }
                return achievements;
            }
        }
    }

    public List<EarnedAchievement> findAllForUser(Long userId) throws SQLException {
        final String sql = "SELECT id, achievement_id, user_id, obtainedat, status, progress "
                + "FROM earnedachievement "
                + "WHERE user_id = ? "
                + "ORDER BY achievement_id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<EarnedAchievement> achievements = new ArrayList<>();
                while (resultSet.next()) {
                    Timestamp obtainedAtTimestamp = resultSet.getTimestamp("obtainedat");
                    LocalDateTime obtainedAt = obtainedAtTimestamp != null ? obtainedAtTimestamp.toLocalDateTime() : null;

                    achievements.add(new EarnedAchievement(
                            resultSet.getLong("id"),
                            resultSet.getLong("achievement_id"),
                            resultSet.getLong("user_id"),
                            obtainedAt,
                            AchievementStatus.valueOf(resultSet.getString("status")),
                            resultSet.getInt("progress")
                    ));
                }
                return achievements;
            }
        }
    }

    public void unlockAchievement(Long userId, Long achievementId) throws SQLException {
        String sql = "UPDATE earnedachievement SET status = 'UNLOCKED', obtainedat = NOW() " +
                "WHERE user_id = ? AND achievement_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, achievementId);
            statement.executeUpdate();
        }
    }

    @Override
    public void update(EarnedAchievement earnedAchievement) throws SQLException {
        String sql = "UPDATE earnedachievement SET "
                + "status = ?, "
                + "progress = ? "
                + "WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, earnedAchievement.getStatus().toString());
            ps.setInt(2, earnedAchievement.getProgress());
            ps.setLong(3, earnedAchievement.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(EarnedAchievement object) {

    }

    @Override
    public boolean save(EarnedAchievement object) throws SQLException {
        return false;
    }
}
