package ru.vsu.amm.java.repo;

import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.enums.AchievementType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AchievementRepository implements Repository<Achievement> {
    private final DataSource dataSource;

    public AchievementRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Achievement> findById(Long id) throws SQLException {
        final String sql = "SELECT id, name, description, type FROM achievement WHERE id = ? ORDER BY id";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Achievement(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            AchievementType.valueOf(resultSet.getString("type"))));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Achievement> findAll() throws SQLException {
        final String sql = "SELECT id, name, description, type FROM achievement ORDER BY id";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Achievement> achievements = new ArrayList<>();

                while (resultSet.next()) {
                    achievements.add(new Achievement(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            AchievementType.valueOf(resultSet.getString("type"))));
                }
                return achievements;
            }
        }
    }

    @Override
    public void update(Achievement object) {

    }

    @Override
    public void delete(Achievement object) {

    }

    @Override
    public boolean save(Achievement object) throws SQLException {
        return false;
    }
}
