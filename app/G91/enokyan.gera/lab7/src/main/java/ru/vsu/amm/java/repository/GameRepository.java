package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.GameEntity;
import ru.vsu.amm.java.util.GameResultMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRepository implements CrudRepository<GameEntity> {
    private static final Logger logger = Logger.getLogger(GameRepository.class.getName());

    private final DataSource dataSource;

    public GameRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
        logger.log(Level.INFO, "Инициализирован репозиторий партий");
    }

    @Override
    public GameEntity findById(long id) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game WHERE id = ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск партии с id={0}", id));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Найдена партия с id={0}", id));
                    return makeGameFromResultSet(resultSet);
                } else {
                    logger.log(Level.FINE, MessageFormat.format("Партия с id={0} не найдена", id));
                }
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [id={1}]", query, id),
                    e
            );
        }

        return null;
    }

    public GameEntity findByIdForUpdate(Connection connection, long id) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game WHERE id = ? FOR UPDATE
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск партии с id={0} для обновления", id));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Найдена партия с id={0} для обновления", id));
                    return makeGameFromResultSet(resultSet);
                } else {
                    logger.log(Level.FINE, MessageFormat.format("Партия с id={0} не найдена для обновления", id));
                }
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [id={1}]", query, id),
                    e
            );
        }

        return null;
    }

    @Override
    public List<GameEntity> findAll() {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game
                """;

        logger.log(Level.FINE, "Поиск всех партий");

        List<GameEntity> games = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(makeGameFromResultSet(resultSet));
            }
            logger.log(Level.FINE, MessageFormat.format("Найдено партий: {0}", games.size()));
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}", query),
                    e
            );
        }

        return games;
    }

    public List<GameEntity> findLast(long count) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game ORDER BY finished DESC LIMIT ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск последних {0} партий", count));

        List<GameEntity> games = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, count);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(makeGameFromResultSet(resultSet));
                }
                logger.log(Level.FINE, MessageFormat.format("Найдено последних партий: {0}", games.size()));
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [count={1}]", query, count),
                    e
            );
        }

        return games;
    }

    public List<GameEntity> findByPlayersId(long id) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game WHERE first_players_id IS NOT NULL AND first_players_id = ? OR
                    second_players_id IS NOT NULL AND second_players_id = ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск партий игрока с id={0}", id));

        List<GameEntity> games = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setLong(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(makeGameFromResultSet(resultSet));
                }
                logger.log(Level.FINE, MessageFormat.format("Найдено партий игрока с id={0}: {1}", id, games.size()));
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [id={1}]", query, id),
                    e
            );
        }

        return games;
    }

    public List<GameEntity> findByPlayersId(Connection connection, long id) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game WHERE first_players_id IS NOT NULL AND first_players_id = ? OR
                    second_players_id IS NOT NULL AND second_players_id = ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск партий игрока с id={0}", id));

        List<GameEntity> games = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setLong(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(makeGameFromResultSet(resultSet));
                }
                logger.log(Level.FINE, MessageFormat.format("Найдено партий игрока с id={0}: {1}", id, games.size()));
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [id={1}]", query, id),
                    e
            );
        }

        return games;
    }

    public List<GameEntity> findLastByPlayersId(long id, long count) {
        final String query = """
                    SELECT id, first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change
                    FROM game WHERE first_players_id IS NOT NULL AND first_players_id = ? OR
                    second_players_id IS NOT NULL AND second_players_id = ?
                    ORDER BY finished DESC LIMIT ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Поиск последних {0} партий игрока с id={1}", count, id));

        List<GameEntity> games = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setLong(2, id);
            statement.setLong(3, count);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(makeGameFromResultSet(resultSet));
                }
                logger.log(Level.FINE, MessageFormat.format("Найдено последних партий игрока с id={0}: {1}", id, games.size()));
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [id={1}, count={2}]", query, id, count),
                    e
            );
        }

        return games;
    }

    @Override
    public long create(GameEntity entity) {
        final String query = """
                    INSERT INTO game (first_players_id, second_players_id, finished, result,
                    first_players_rating_before, second_players_rating_before,
                    first_players_rating_change, second_players_rating_change) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        logger.log(Level.FINE, MessageFormat.format("Добавление новой партии {0}", entity));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertOrUpdateStatement(statement, entity);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Добавлена новая партия {0}", entity));
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при добавлении партии {0}", entity), e);
        }

        return -1;
    }

    @Override
    public void update(GameEntity entity) {
        final String query = """
                    UPDATE game SET first_players_id = ?, second_players_id = ?, finished = ?, result = ?,
                    first_players_rating_before = ?, second_players_rating_before = ?,
                    first_players_rating_change = ?, second_players_rating_change = ? WHERE id = ?
                """;

        logger.log(Level.FINE, MessageFormat.format("Обновление партии {0}", entity));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertOrUpdateStatement(statement, entity);
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
            logger.log(Level.FINE, MessageFormat.format("Обновлена партия {0}", entity));
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при обновлении партии {0}", entity), e);
        }
    }

    @Override
    public void delete(Connection connection, GameEntity entity) {
        final String query = "DELETE FROM game WHERE id = ?";

        logger.log(Level.FINE, MessageFormat.format("Удаление партии {0}", entity));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
            logger.log(Level.FINE, MessageFormat.format("Удалена партия {0}", entity));
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при удалении партии {0}", entity), e);
        }
    }

    private GameEntity makeGameFromResultSet(ResultSet resultSet) throws SQLException {
        return new GameEntity(
                resultSet.getLong("id"),
                resultSet.getLong("first_players_id"),
                resultSet.getLong("second_players_id"),
                resultSet.getTimestamp("finished").toLocalDateTime(),
                GameResultMapper.mapStringToGameResult(resultSet.getString("result")),
                resultSet.getDouble("first_players_rating_before"),
                resultSet.getDouble("second_players_rating_before"),
                resultSet.getDouble("first_players_rating_change"),
                resultSet.getDouble("second_players_rating_change")
        );
    }

    private void prepareInsertOrUpdateStatement(PreparedStatement statement, GameEntity entity)
            throws SQLException {
        statement.setLong(1, entity.getFirstPlayersId());
        statement.setLong(2, entity.getSecondPlayersId());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getFinished()));
        statement.setString(4, GameResultMapper.mapGameResultToString(entity.getResult()));
        statement.setDouble(5, entity.getFirstPlayersRatingBefore());
        statement.setDouble(6, entity.getSecondPlayersRatingBefore());
        statement.setDouble(7, entity.getFirstPlayersRatingChange());
        statement.setDouble(8, entity.getSecondPlayersRatingChange());
    }
}