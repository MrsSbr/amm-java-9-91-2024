package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DatabaseConfiguration;
import ru.vsu.amm.java.Entities.Incident;
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

public class IncidentRepository implements DatabaseRepository<Incident> {

    private final DataSource dataSource;

    public IncidentRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Incident> findById(Long id) throws SQLException {
        final String query = "SELECT IdIncident, EmplId, DinoId, DateOfIncident, Description FROM Incident WHERE IdIncident = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new Incident(
                    resultSet.getLong("IdIncident"),
                    resultSet.getLong("EmplId"),
                    resultSet.getLong("DinoId"),
                    resultSet.getLocalDate("DateOfIncident"),
                    resultSet.getString("Description")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Incident> findAll() throws SQLException {
        final String query = "SELECT IdIncident, EmplId, DinoId, DateOfIncident, Description FROM Incident";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Incident> incidents = new ArrayList<>();
        while (resultSet.next()) {
            incidents.add(new Incident(
                    resultSet.getLong("IdIncident"),
                    resultSet.getLong("EmplId"),
                    resultSet.getLong("DinoId"),
                    resultSet.getLocalDate("DateOfIncident"),
                    resultSet.getString("Description")
            ));
        }

        return incidents;
    }

    @Override
    public void save(Incident entity) throws SQLException {
        final String query = "INSERT INTO Incident (EmplId, DinoId, DateOfIncident, Description) VALUES (?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getEmplId());
        preparedStatement.setLong(2, entity.getDinoId());
        preparedStatement.setLocalDate(3, entity.getDateOfIncident());
        preparedStatement.setString(4, entity.getDescription());

        preparedStatement.execute();
    }

    @Override
    public void delete(Incident entity) throws SQLException {
        final String query = "DELETE FROM Incident WHERE IdIncident = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getIdIncident());

        preparedStatement.execute();
    }

    @Override
    public void update(Incident entity) throws SQLException {
        final String query = "UPDATE Incident SET EmplId = ?, DinoId = ?, DateOfIncident = ?, Description = ? WHERE IdIncident = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, entity.getEmplId());
        preparedStatement.setLong(2, entity.getDinoId());
        preparedStatement.setLocalDate(3, entity.getDateOfIncident());
        preparedStatement.setString(4, entity.getDescription());
        preparedStatement.setLong(5, entity.getIdIncident());

        preparedStatement.execute();
    }
}