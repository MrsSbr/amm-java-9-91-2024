package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Enums.ObjectType;
import ru.vsu.amm.java.Enums.Roles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalObjectRepository implements DatabaseRepository<RentalObjectEntity> {

    private final DataSource dataSource;

    public RentalObjectRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<RentalObjectEntity> findById(Long id) throws SQLException {
        final String query = "SELECT objectID, objectName, objectType, info, price FROM RentalObjectTable WHERE objectID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            ObjectType objectType = ObjectType.valueOf(resultSet.getString("objectType"));
            return Optional.of(new RentalObjectEntity(
                    resultSet.getLong("objectID"),
                    resultSet.getString("objectName"),
                    objectType,
                    resultSet.getString("info"),
                    resultSet.getInt("price")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<RentalObjectEntity> findAll() throws SQLException {
        final String query = "SELECT objectID, objectName, objectType, info, price FROM RentalObjectTable";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        List<RentalObjectEntity> rentalObjects = new ArrayList<>();
        while (resultSet.next()) {
            ObjectType objectType = ObjectType.valueOf(resultSet.getString("objectType"));
            rentalObjects.add(new RentalObjectEntity(
                    resultSet.getLong("objectID"),
                    resultSet.getString("objectName"),
                    objectType,
                    resultSet.getString("info"),
                    resultSet.getInt("price")
            ));
        }

        return rentalObjects;
    }

    @Override
    public void save(RentalObjectEntity entity) throws SQLException {
        final String query = "INSERT INTO RentalObjectTable (objectName, objectType, info, price) VALUES (?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getObjectName());
        preparedStatement.setString(2, entity.getObjectType().name());
        preparedStatement.setString(3, entity.getInfo());
        preparedStatement.setInt(4, entity.getPrice());

        preparedStatement.execute();
    }

    @Override
    public void delete(RentalObjectEntity entity) throws SQLException {
        final String query = "DELETE FROM RentalObjectTable WHERE objectID = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getObjectID());

        preparedStatement.execute();
    }

    @Override
    public void update(RentalObjectEntity entity) throws SQLException {
        final String query = "UPDATE RentalObjectTable SET objectName = ?, objectType = ?, info = ?, price = ? WHERE objectID = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getObjectName());
        preparedStatement.setString(2, entity.getObjectType().toString());
        preparedStatement.setString(3, entity.getInfo());
        preparedStatement.setInt(4, entity.getPrice());
        preparedStatement.setLong(5, entity.getObjectID());

        preparedStatement.execute();
    }

}