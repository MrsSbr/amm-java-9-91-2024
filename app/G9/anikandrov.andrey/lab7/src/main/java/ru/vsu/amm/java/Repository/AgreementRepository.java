package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.AgreementEntity;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgreementRepository implements DatabaseRepository<AgreementEntity> {

    private final DataSource dataSource;

    private static final Logger logger = Logger.getLogger(AgreementRepository.class.getName());

    public AgreementRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<AgreementEntity> findById(Long id) throws SQLException {
        logger.log(Level.INFO, "Try to find agreement by ID: " + id);

        final String query = """
                             SELECT agreement_id, user_id, object_id, time_start, time_end, sum_price
                             FROM Agreement_Table
                             WHERE agreement_id = ?
                             """;

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            logger.log(Level.INFO, "Successfully found agreement with ID: " + id);

            return Optional.of(new AgreementEntity(
                    resultSet.getLong("agreement_ID"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("object_id"),
                    resultSet.getDate("time_start").toLocalDate(),
                    resultSet.getDate("time_end").toLocalDate(),
                    resultSet.getInt("sum_price")
            ));
        }

        logger.log(Level.WARNING, "Agreement not found with ID: " + id);
        return Optional.empty();
    }

    @Override
    public List<AgreementEntity> findAll() throws SQLException {
        logger.log(Level.INFO, "Try to find all agreements");
        final String query = """
                             SELECT agreement_id, user_id, object_id, time_start, time_end, sum_price
                             FROM Agreement_Table
                             """;

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<AgreementEntity> agreements = new ArrayList<>();
        while (resultSet.next()) {
            agreements.add(new AgreementEntity(
                    resultSet.getLong("agreement_id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("object_id"),
                    resultSet.getDate("time_start").toLocalDate(),
                    resultSet.getDate("time_end").toLocalDate(),
                    resultSet.getInt("sum_price")
            ));
        }

        logger.log(Level.INFO, "Found " + agreements.size() + " agreements");
        return agreements;
    }

    @Override
    public void save(AgreementEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to save agreement for user ID: " + entity.getUserID());
        final String query = """
                             INSERT INTO agreement_table (user_id, object_id, time_start, time_end, sum_price) 
                             VALUES (?, ?, ?, ?, ?)
                             """;

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getUserID());
        preparedStatement.setLong(2, entity.getObjectID());
        preparedStatement.setDate(3, Date.valueOf(entity.getTimeStart()));
        preparedStatement.setDate(4, Date.valueOf(entity.getTimeEnd()));
        preparedStatement.setInt(5, entity.getSumPrice());

        preparedStatement.execute();
        logger.log(Level.INFO, "Successfully saved agreement for user ID: " + entity.getUserID());
    }

    @Override
    public void delete(AgreementEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to delete agreement with ID: " + entity.getAgreementID());
        final String query = """
                            DELETE FROM agreement_table
                            WHERE agreement_id = ?
                            """;

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getAgreementID());

        preparedStatement.execute();
        logger.log(Level.INFO, "Successfully deleted agreement with ID: " + entity.getAgreementID());
    }

    @Override
    public void update(AgreementEntity entity) throws SQLException {
        logger.log(Level.INFO, "Try to update agreement with ID: " + entity.getAgreementID());
        final String query = """
                             UPDATE agreement_table
                             SET user_id = ?, object_id = ?, time_start = ?, time_end = ?, sum_price = ?
                             WHERE agreement_id = ?
                             """;

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getUserID());
        preparedStatement.setLong(2, entity.getObjectID());
        preparedStatement.setDate(3, Date.valueOf(entity.getTimeStart()));
        preparedStatement.setDate(4, Date.valueOf(entity.getTimeEnd()));
        preparedStatement.setInt(5, entity.getSumPrice());
        preparedStatement.setLong(6, entity.getAgreementID());

        preparedStatement.execute();
        logger.log(Level.INFO, "Successfully updated agreement with ID: " + entity.getAgreementID());
    }

}
