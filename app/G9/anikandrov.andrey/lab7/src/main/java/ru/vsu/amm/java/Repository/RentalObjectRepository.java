package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Enums.ObjectType;

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
        final String query = "SELECT object_id, object_name, object_type, object_info, price FROM rentalobject_table WHERE object_id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            ObjectType objectType = ObjectType.valueOf(resultSet.getString("object_type"));
            return Optional.of(new RentalObjectEntity(
                    resultSet.getLong("object_id"),
                    resultSet.getString("object_name"),
                    objectType,
                    resultSet.getString("object_info"),
                    resultSet.getInt("price")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<RentalObjectEntity> findAll() throws SQLException {
        final String query = "SELECT object_id, object_name, object_type, object_info, price FROM rentalobject_table";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        List<RentalObjectEntity> rentalObjects = new ArrayList<>();
        while (resultSet.next()) {
            ObjectType objectType = ObjectType.valueOf(resultSet.getString("object_type"));
            rentalObjects.add(new RentalObjectEntity(
                    resultSet.getLong("object_id"),
                    resultSet.getString("object_name"),
                    objectType,
                    resultSet.getString("object_info"),
                    resultSet.getInt("price")
            ));
        }

        return rentalObjects;
    }

    @Override
    public void save(RentalObjectEntity entity) throws SQLException {
        final String query = "INSERT INTO rentalobject_table (object_name, object_type, object_info, price) VALUES (?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getObjectName());
        preparedStatement.setString(2, entity.getObjectType().name());
        preparedStatement.setString(3, entity.getObjectInfo());
        preparedStatement.setInt(4, entity.getPrice());

        preparedStatement.execute();
    }

    @Override
    public void delete(RentalObjectEntity entity) throws SQLException {
        final String query = "DELETE FROM rentalobject_table WHERE object_id = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getObjectID());

        preparedStatement.execute();
    }

    @Override
    public void update(RentalObjectEntity entity) throws SQLException {
        final String query = "UPDATE rentalobject_table SET object_name = ?, object_type = ?, object_info = ?, price = ? WHERE object_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, entity.getObjectName());
        preparedStatement.setString(2, entity.getObjectType().toString());
        preparedStatement.setString(3, entity.getObjectInfo());
        preparedStatement.setInt(4, entity.getPrice());
        preparedStatement.setLong(5, entity.getObjectID());

        preparedStatement.execute();
    }

}