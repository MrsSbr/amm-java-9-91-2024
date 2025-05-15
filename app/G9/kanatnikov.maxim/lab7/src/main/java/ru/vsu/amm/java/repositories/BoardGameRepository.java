package ru.vsu.amm.java.repositories;

import ru.vsu.amm.java.configuration.DbConfiguration;
import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.enums.Genre;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardGameRepository implements Repository<BoardGame> {
    private static final Logger logger = Logger.getLogger(BoardGameRepository.class.getName());
    private final DataSource dataSource;

    public BoardGameRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    public BoardGameRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<BoardGame> findById(Long id) throws SQLException {
        final String query = """
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame WHERE Board_Game_Id = ?
                """;
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                return Optional.of(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<BoardGame> findByName(String name) throws SQLException {
        final String query = """
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame WHERE "Name" = ?
                """;
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                return Optional.of(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    public List<BoardGame> findBy(String field, String value) throws SQLException {
        List<BoardGame> boardGames = new ArrayList<>();
        final String query = String.format("""
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame WHERE %s ILIKE ?
                """, field);
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "%" + value + "%");
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                boardGames.add(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return boardGames;
    }

    public List<BoardGame> findByPrice(int minPrice, int maxPrice) throws SQLException {
        List<BoardGame> boardGames = new ArrayList<>();
        final String query = """
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame WHERE Price BETWEEN ? AND ?
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                boardGames.add(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return boardGames;
    }

    public List<BoardGame> findByAge(int minAge) throws SQLException {
        List<BoardGame> boardGames = new ArrayList<>();
        final String query = """
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame WHERE Min_Age <= ?
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, minAge);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                boardGames.add(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return boardGames;
    }

    @Override
    public List<BoardGame> findAll() throws SQLException {
        List<BoardGame> boardGames = new ArrayList<>();

        final String query = """
                SELECT Board_Game_Id, "Name", Price, Genre, Min_Age, Publisher, Description
                FROM BoardGame
                """;
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Genre genre = Genre.valueOf(resultSet.getString("Genre"));
                boardGames.add(new BoardGame(
                        resultSet.getLong("Board_Game_Id"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Price"),
                        genre,
                        resultSet.getInt("Min_Age"),
                        resultSet.getString("Publisher"),
                        resultSet.getString("Description")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return boardGames;
    }

    @Override
    public void update(BoardGame entity) throws SQLException {
        final String query = """
                UPDATE BoardGame SET "Name" = ?, Price = ?, Genre = ?, Min_Age = ?,
                Publisher = ?, Description = ? WHERE Board_Game_Id = ?
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getPrice());
            preparedStatement.setString(3, entity.getGenre().name());
            preparedStatement.setInt(4, entity.getMinAge());
            preparedStatement.setString(5, entity.getPublisher());
            preparedStatement.setString(6, entity.getDescription());
            preparedStatement.setLong(7, entity.getBoardGameId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void save(BoardGame entity) throws SQLException {
        final String query = """
                INSERT INTO BoardGame ("Name", Price, Genre, Min_Age, Publisher, Description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getPrice());
            preparedStatement.setString(3, entity.getGenre().name());
            preparedStatement.setInt(4, entity.getMinAge());
            preparedStatement.setString(5, entity.getPublisher());
            preparedStatement.setString(6, entity.getDescription());


            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(BoardGame entity) throws SQLException {
        final String query = "DELETE FROM BoardGame WHERE Board_Game_Id = ?";

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, entity.getBoardGameId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }
}
