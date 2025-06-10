package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Incident;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IncidentRepository implements DbRepository<Incident> {

    private final DataSource dataSource;

    public IncidentRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Incident> findById(Long id) throws SQLException {
        final String query = "SELECT IdIncident, EmplId, DinoId, DateOfIncident, Description  FROM Incident WHERE IdIncident = ?";

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
                    resultSet.getDate("DateOfIncident").toLocalDate(),
                    resultSet.getString("Description")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Incident> findAll() throws SQLException {
        final String query = "SELECT IdIncident, EmplId, DinoId, DateOfIncident, description FROM Incident";

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Incident> incidents = new ArrayList<>();
        while (resultSet.next()) {
            incidents.add(new Incident(
                    resultSet.getLong("IdIncident"),
                    resultSet.getLong("EmplId"),
                    resultSet.getLong("DinoId"),
                    resultSet.getDate("DateOfIncident").toLocalDate(),
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
        preparedStatement.setDate(3, Date.valueOf(entity.getDateOfIncident()));
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
        preparedStatement.setDate(3, Date.valueOf(entity.getDateOfIncident()));
        preparedStatement.setString(4, entity.getDescription());
        preparedStatement.setLong(5, entity.getIdIncident());

        preparedStatement.execute();
    }
}