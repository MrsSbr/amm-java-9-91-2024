package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.AgreementEntity;
import ru.vsu.amm.java.Entities.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgreementRepository implements DatabaseRepository<AgreementEntity> {

    private final DataSource dataSource;

    public AgreementRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<AgreementEntity> findById(Long id) throws SQLException {
        final String query = "SELECT agreementID, userID, objectID, timeStart, timeEnd, sumPrice FROM AgreementTable WHERE agreementID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new AgreementEntity(
                    resultSet.getLong("agreementID"),
                    resultSet.getLong("userID"),
                    resultSet.getLong("objectID"),
                    resultSet.getDate("timeStart").toLocalDate(),
                    resultSet.getDate("timeEnd").toLocalDate(),
                    resultSet.getInt("sumPrice")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<AgreementEntity> findAll() throws SQLException {
        final String query = "SELECT agreementID, userID, objectID, timeStart, timeEnd, sumPrice FROM AgreementTable";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<AgreementEntity> agreements = new ArrayList<>();
        while (resultSet.next()) {
            agreements.add(new AgreementEntity(
                    resultSet.getLong("agreementID"),
                    resultSet.getLong("userID"),
                    resultSet.getLong("objectID"),
                    resultSet.getDate("timeStart").toLocalDate(),
                    resultSet.getDate("timeEnd").toLocalDate(),
                    resultSet.getInt("sumPrice")
            ));
        }

        return agreements;
    }

    @Override
    public void save(AgreementEntity entity) throws SQLException {
        final String query = "INSERT INTO AgreementTable (userID, objectID, timeStart, timeEnd, sumPrice) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getUserID());
        preparedStatement.setLong(2, entity.getObjectID());
        preparedStatement.setDate(3, Date.valueOf(entity.getTimeStart()));
        preparedStatement.setDate(4, Date.valueOf(entity.getTimeEnd()));
        preparedStatement.setInt(5, entity.getSumPrice());

        preparedStatement.execute();
    }

    @Override
    public void delete(AgreementEntity entity) throws SQLException {
        final String query = "DELETE FROM AgreementTable WHERE agreementID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getAgreementID());

        preparedStatement.execute();
    }

    @Override
    public void update(AgreementEntity entity) throws SQLException {
        final String query = "UPDATE AgreementTable SET userID = ?, objectID = ?, timeStart = ?, timeEnd = ?, sumPrice = ? WHERE agreementID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getUserID());
        preparedStatement.setLong(2, entity.getObjectID());
        preparedStatement.setDate(3, Date.valueOf(entity.getTimeStart()));
        preparedStatement.setDate(4, Date.valueOf(entity.getTimeEnd()));
        preparedStatement.setInt(5, entity.getSumPrice());
        preparedStatement.setLong(6, entity.getAgreementID());

        preparedStatement.execute();
    }

}
