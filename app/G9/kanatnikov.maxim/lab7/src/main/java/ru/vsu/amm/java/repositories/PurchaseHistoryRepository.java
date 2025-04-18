package ru.vsu.amm.java.repositories;

import ru.vsu.amm.java.configuration.DbConfiguration;
import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.entities.PurchaseHistory;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.responces.PurchaseHistoryResponce;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseHistoryRepository implements Repository<PurchaseHistory> {
    private static final Logger logger = Logger.getLogger(PurchaseHistoryRepository.class.getName());
    private final DataSource dataSource;

    public PurchaseHistoryRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<PurchaseHistory> findById(Long id) throws SQLException {
        final String query = "SELECT Order_Number, Payment, User_Id, Board_Game_Id " +
                "FROM PurchaseHistory WHERE Order_Number = ?";
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                return Optional.of(new PurchaseHistory(
                        resultSet.getLong("Order_Number"),
                        resultSet.getInt("Payment"),
                        resultSet.getLong("User_Id"),
                        resultSet.getLong("Board_Game_Id")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<PurchaseHistory> findAll() throws SQLException {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        final String query = "SELECT Order_Number, Payment, User_Id, Board_Game_Id " +
                "FROM PurchaseHistory";
        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                purchaseHistories.add(new PurchaseHistory(
                        resultSet.getLong("Order_Number"),
                        resultSet.getInt("Payment"),
                        resultSet.getLong("User_Id"),
                        resultSet.getLong("Board_Game_Id")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }

        return purchaseHistories;
    }

    public List<PurchaseHistoryResponce> findByUserIdWithGames(Long userId) throws SQLException {
        List<PurchaseHistoryResponce> responses = new ArrayList<>();
        String query = """
        SELECT ph.order_number, ph.payment, bg.* 
        FROM PurchaseHistory ph
        JOIN BoardGame bg ON ph.board_game_id = bg.board_game_id
        WHERE ph.user_id = ?
        """;

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BoardGame game = new BoardGame(
                        resultSet.getLong("board_game_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        Genre.valueOf(resultSet.getString("genre")),
                        resultSet.getInt("min_age"),
                        resultSet.getString("publisher"),
                        resultSet.getString("description")
                );

                responses.add(new PurchaseHistoryResponce(
                        resultSet.getLong("order_number"),
                        resultSet.getInt("payment"),
                        game
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching purchases", e);
            throw new SQLException(e);
        }

        return responses;
    }

    @Override
    public void update(PurchaseHistory entity) throws SQLException {
        final String query = "UPDATE PurchaseHistory SET Payment = ?, " +
                "User_Id = ?, Board_Game_Id = ? WHERE Order_Number = ?";

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, entity.getPayment());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.setLong(3, entity.getBoardGameId());
            preparedStatement.setLong(4, entity.getOrderNumber());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void save(PurchaseHistory entity) throws SQLException {
        final String query = "INSERT INTO PurchaseHistory (Payment, User_Id, Board_Game_Id) VALUES (?, ?, ?)";

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, entity.getPayment());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.setLong(3, entity.getBoardGameId());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(PurchaseHistory entity) throws SQLException {
        final String query = "DELETE FROM PurchaseHistory WHERE Order_Number = ?";

        try (var connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, entity.getOrderNumber());

            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e.getMessage());
        }
    }
}
