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
import java.util.ArrayList;
import java.util.List;

public class GameRepository implements CrudRepository<GameEntity> {

    private final DataSource dataSource;

    public GameRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public GameEntity findById(long id) {
        final String query = """
                    SELECT id, first_player_id, second_player_id, finished, result,
                    first_players_rating_before, second_players_rating_before FROM game WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new GameEntity(resultSet.getLong("id"), resultSet.getLong("first_player_id"), resultSet.getLong("first_player_id"), resultSet.getTimestamp("finished").toLocalDateTime(), GameResultMapper.mapStringToGameResult(resultSet.getString("result")), resultSet.getDouble("first_players_rating_before"), resultSet.getDouble("first_players_rating_before"));
            }
        } catch (SQLException e) {
            // log
        }

        return null;
    }

    @Override
    public List<GameEntity> findAll() {
        List<GameEntity> users = new ArrayList<>();

        final String query = """
                    SELECT id, first_player_id, second_player_id, finished, result,
                    first_players_rating_before, second_players_rating_before FROM game
                """;

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(new GameEntity(resultSet.getLong("id"), resultSet.getLong("first_player_id"), resultSet.getLong("first_player_id"), resultSet.getTimestamp("finished").toLocalDateTime(), GameResultMapper.mapStringToGameResult(resultSet.getString("result")), resultSet.getDouble("first_players_rating_before"), resultSet.getDouble("first_players_rating_before")));
            }
        } catch (SQLException e) {
            // log
        }

        return users;
    }

    @Override
    public void create(GameEntity entity) {
        final String query = """
                    INSERT INTO game (first_player_id, second_player_id, finished, result,
                    first_players_rating_before, second_players_rating_before) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getFirstPlayerId());
            statement.setLong(2, entity.getSecondPlayerId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFinished()));
            statement.setString(4, GameResultMapper.mapGameResultToString(entity.getResult()));
            statement.setDouble(5, entity.getFirstPlayersRating());
            statement.setDouble(6, entity.getSecondPlayersRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    @Override
    public void update(GameEntity entity) {
        final String query = """
                    UPDATE game SET first_player_id = ?, second_player_id = ?, finished = ?, result = ?,
                    first_players_rating_before = ?, second_players_rating_before = ? WHERE id = ?
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getFirstPlayerId());
            statement.setLong(2, entity.getSecondPlayerId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFinished()));
            statement.setString(4, GameResultMapper.mapGameResultToString(entity.getResult()));
            statement.setDouble(5, entity.getFirstPlayersRating());
            statement.setDouble(6, entity.getSecondPlayersRating());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    @Override
    public void delete(GameEntity entity) {
        final String query = "DELETE FROM game WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }
}