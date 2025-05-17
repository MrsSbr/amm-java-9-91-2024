package ru.vsu.amm.java.repo;

import ru.vsu.amm.java.entity.EarnedAchievement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import ru.vsu.amm.java.enums.AchievementStatus;

import java.util.List;

public class EarnedAchievementRepository implements Repository<EarnedAchievement> {
    private final DataSource dataSource;

    public EarnedAchievementRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<EarnedAchievement> findById(final Long id) throws SQLException {
        final String sql = "SELECT id, achievement_id, user_id, obtainedat, status FROM earnedachievement WHERE id = ?";
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
                            AchievementStatus.valueOf(resultSet.getString("status"))));
                }
            }
            return Optional.empty();
        }
    }


    @Override
    public List<EarnedAchievement> findAll() throws SQLException {
        final String sql = "SELECT id, achievement_id, user_id, obtainedat, status FROM earnedachievement ORDER BY achievement_id";
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
                            AchievementStatus.valueOf(resultSet.getString("status"))));
                }
                return achievements;
            }
        }
    }

    @Override
    public void update(EarnedAchievement object) {

    }

    @Override
    public void delete(EarnedAchievement object) {

    }

    @Override
    public void save(EarnedAchievement object) throws SQLException {

    }
}
