package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.Dino;
import ru.vsu.amm.java.Enums.Roles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DinoRepository implements DatabaseRepository<Dino> {

    private final DataSource dataSource;

    public DinoRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Dino> findById(Long id) throws SQLException {
        final String query = "SELECT IdDino, Weight, DateOfBirthDino, DateOfDeath, KindOfDino, NameDino FROM Dino WHERE IdDino = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            Kind kind = Kind.valueOf(resultSet.getString("Kind"));
            return Optional.of(new Dino(
                    resultSet.getLong("IdDino"),
                    resultSet.getDouble("Weight"),
                    resultSet.getLocalDate("DateOfDeath"),
                    resultSet.getLocalDate("DateOfBirthDino"),
                    kind,
                    resultSet.getString("NameDino")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Dino> findAll() throws SQLException {
        final String query = "SELECT IdDino, Weight, DateOfBirthDino, DateOfDeath, KindOfDino, NameDino FROM Dino";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Dino> dinos = new ArrayList<>();
        while (resultSet.next()) {
            Kind kind = Kind.valueOf(resultSet.getString("Kind"));
            dinos.add(new Dino(
                    resultSet.getLong("IdDino"),
                    resultSet.getDouble("Weight"),
                    resultSet.getLocalDate("DateOfDeath"),
                    resultSet.getLocalDate("DateOfBirthDino"),
                    kind,
                    resultSet.getString("NameDino")
            ));
        }

        return dinos;
    }

    @Override
    public void save(Dino entity) throws SQLException {
        final String query = "INSERT INTO Dino (Weight, DateOfBirthDino, DateOfDeath, KindOfDino, NameDino) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setDouble(1, entity.getWeight());
        preparedStatement.setLocalDate(2, entity.getDateOfBirthDino());
        preparedStatement.setLocalDate(3, entity.getDateOfDeath());
        preparedStatement.setString(4, entity.getKindOfDino());
        preparedStatement.setString(5, entity.getNameDino());

        preparedStatement.execute();
    }

    @Override
    public void delete(Dino entity) throws SQLException {
        final String query = "DELETE FROM Dino WHERE IdDino = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getIdDino());

        preparedStatement.execute();
    }

    @Override
    public void update(Dino entity) throws SQLException {
        final String query = "UPDATE Dino SET Weight = ?, DateOfBirthDino = ?, DateOfDeath = ?, KindOfDino = ?, NameDino = ? WHERE IdDino = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setDouble(1, entity.getWeight());
        preparedStatement.setLocalDate(2, entity.getDateOfBirthDino());
        preparedStatement.setLocalDate(3, entity.getDateOfDeath());
        preparedStatement.setString(4, entity.getKindOfDino());
        preparedStatement.setString(5, entity.getNameDino());
        preparedStatement.setLong(6, entity.getIdDino());

        preparedStatement.execute();
    }
}