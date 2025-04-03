package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.Housing;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entity.RealEstate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class RealEstateRepository implements BaseRepository<RealEstate> {

    private final DataSource dataSource;

    public RealEstateRepository() {

        dataSource = DatabaseConfig.getDataSource();
    }
    @Override
    public Optional<RealEstate> findById(Long id) throws SQLException {
        final String query = """
                SELECT id, type, address, maximumNumberOfGuests, rules
                FROM realEstate WHERE id = ?
                """;
        var connection = dataSource.getConnection();

        var pStmt = connection.prepareStatement(query);
        pStmt.setLong(1, id);
        pStmt.execute();

        var result = pStmt.getResultSet();
        if (result.next()) {
            return Optional.of(new RealEstate(
                    result.getLong("id"),
                    Housing.valueOf(result.getString("type")),
                    result.getString("address"),
                    result.getInt("maximumNumberOfGuests"),
                    result.getString("rules")
            ));
        }
        return Optional.empty();
    }

    @Override
    public void update(RealEstate entity) throws SQLException {
        final String query = """
                UPDATE realEstate
                SET type = ?, address = ?, maximumNumberOfGuests = ?, rules = ?
                WHERE id = ?""";
        var connection = dataSource.getConnection();
        var pStmt = connection.prepareStatement(query);
        setPrepareStatement(pStmt, entity);
        pStmt.setLong(5, entity.getId());
        pStmt.execute();

    }

    private void setPrepareStatement(PreparedStatement pStmt, RealEstate entity) throws SQLException {
        pStmt.setString(1, entity.getType().toString());
        pStmt.setString(2, entity.getAddress());
        pStmt.setInt(3, entity.getMaximumNumberOfGuests());
        pStmt.setString(4, entity.getRules());

    }

    @Override
    public void save(RealEstate entity) throws SQLException {
        final String query = """
                INSERT INTO realEstate (type, address, maximumNumberOfGuests, rules)
                VALUES (?, ?, ?, ?)
                """;
        var connection = dataSource.getConnection();
        var pStmt = connection.prepareStatement(query);
        setPrepareStatement(pStmt, entity);
        pStmt.execute();
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM realEstate where id = ?";
        var connection = dataSource.getConnection();
        var pStmt = connection.prepareStatement(query);
        pStmt.setLong(1, id);
        pStmt.execute();
    }
}
