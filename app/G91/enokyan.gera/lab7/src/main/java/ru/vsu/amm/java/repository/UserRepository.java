package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.util.RolesMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements CrudRepository<UserEntity> {
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
        logger.log(Level.INFO, "Инициализирован репозиторий пользователей");
    }

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        logger.log(Level.INFO, "Инициализирован репозиторий пользователей с заданным dataSource");
    }

    @Override
    public UserEntity findById(long id) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user WHERE id = ?";

        logger.log(Level.FINE, MessageFormat.format("Поиск пользователя с id={0}", id));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Найден пользователь с id={0}", id));
                    return makeUserFromResultSet(resultSet);
                } else {
                    logger.log(Level.FINE, MessageFormat.format("Пользователь с id={0} не найден", id));
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

    public UserEntity findByNickname(String nickname) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user WHERE nickname = ?";

        logger.log(Level.FINE, MessageFormat.format("Поиск пользователя с nickname={0}", nickname));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nickname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Найден пользователь с nickname={0}", nickname));
                    return makeUserFromResultSet(resultSet);
                } else {
                    logger.log(Level.FINE, MessageFormat.format("Пользователь с nickname={0} не найден", nickname));
                }
            }
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}\nс параметрами [nickname={1}]", query, nickname),
                    e
            );
        }

        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user";

        logger.log(Level.FINE, "Поиск всех пользователей");

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(makeUserFromResultSet(resultSet));
            }
            logger.log(Level.FINE, MessageFormat.format("Найдено пользователей: {0}", users.size()));
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}", query),
                    e
            );
        }

        return users;
    }

    public List<UserEntity> findTopRated(long count) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user ORDER BY rating DESC, nickname LIMIT ?";

        logger.log(Level.FINE, MessageFormat.format("Поиск топ-{0} пользователей", count));

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(makeUserFromResultSet(resultSet));
            }
            logger.log(Level.FINE, MessageFormat.format("Найдено топ-{0} пользователей", users.size()));
        } catch (SQLException e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Ошибка при выполнении запроса\n{0}", query),
                    e
            );
        }

        return users;
    }

    @Override
    public long create(UserEntity entity) {
        final String query = "INSERT INTO elo_user (nickname, password, rating, roles) VALUES (?, ?, ?, ?)";

        logger.log(Level.FINE, MessageFormat.format("Добавление нового пользователя {0}", entity));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareInsertOrUpdateStatement(connection, statement, entity);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    logger.log(Level.FINE, MessageFormat.format("Добавлен новый пользователь {0}", entity));
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при добавлении пользователя {0}", entity), e);
        }

        return -1;
    }

    @Override
    public void update(UserEntity entity) {
        final String query = "UPDATE elo_user SET nickname = ?, password = ?, rating = ?, roles = ? WHERE id = ?";

        logger.log(Level.FINE, MessageFormat.format("Обновление пользователя {0}", entity));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertOrUpdateStatement(connection, statement, entity);
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
            logger.log(Level.FINE, MessageFormat.format("Обновлен пользователь {0}", entity));
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при обновлении пользователя {0}", entity), e);
        }
    }

    @Override
    public void delete(UserEntity entity) {
        final String query = "DELETE FROM elo_user WHERE id = ?";

        logger.log(Level.FINE, MessageFormat.format("Удаление пользователя {0}", entity));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
            logger.log(Level.FINE, MessageFormat.format("Удален пользователь {0}", entity));
        } catch (SQLException e) {
            logger.log(Level.FINE, MessageFormat.format("Ошибка при удалении пользователя {0}", entity), e);
        }
    }

    private UserEntity makeUserFromResultSet(ResultSet resultSet) throws SQLException {
        Array array = resultSet.getArray("roles");
        String[] textArray = (String[]) array.getArray();
        List<Role> roles = RolesMapper.mapArrayOfStringsToRoles(textArray);
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("nickname"),
                resultSet.getString("password"),
                resultSet.getDouble("rating"), roles
        );
    }

    private void prepareInsertOrUpdateStatement(Connection connection, PreparedStatement statement, UserEntity entity)
            throws SQLException {
        Array roles = connection.createArrayOf("TEXT", RolesMapper.mapRolesToArrayOfStrings(entity.getRoles()));
        statement.setString(1, entity.getNickname());
        statement.setString(2, entity.getPassword());
        statement.setDouble(3, entity.getRating());
        statement.setArray(4, roles);
    }
}