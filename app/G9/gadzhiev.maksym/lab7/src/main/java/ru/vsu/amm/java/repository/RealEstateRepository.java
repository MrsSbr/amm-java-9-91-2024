package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.Housing;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.entity.RealEstate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RealEstateRepository implements BaseRepository<RealEstate> {

    private final DataSource dataSource;

    public RealEstateRepository() {

        dataSource = DatabaseConfig.getDataSource();
    }
    @Override
    public Optional<RealEstate> findById(Long id) throws SQLException {
        final String query = """
                SELECT id, type, address, maximum_number_of_guests, rules, image_name, price
                FROM real_estate WHERE id = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setLong(1, id);

            try (ResultSet result = pStmt.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new RealEstate(
                            result.getLong("id"),
                            Housing.valueOf(result.getString("type")),
                            result.getString("address"),
                            result.getInt("maximum_number_of_guests"),
                            result.getString("rules"),
                            result.getString("image_name"),
                            result.getLong("price")));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void update(RealEstate entity) throws SQLException {
        final String query = """
                UPDATE realEstate
                SET type = ?, address = ?, maximum_number_of_guests = ?, rules = ?, image_name = ?, price = ?
                WHERE id = ?""";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPrepareStatement(pStmt, entity);
            pStmt.setLong(7, entity.getId());
            pStmt.execute();
        }
    }

    private void setPrepareStatement(PreparedStatement pStmt, RealEstate entity) throws SQLException {
        pStmt.setString(1, entity.getType().toString());
        pStmt.setString(2, entity.getAddress());
        pStmt.setInt(3, entity.getMaximumNumberOfGuests());
        pStmt.setString(4, entity.getRules());
        pStmt.setString(5, entity.getImageName());
        pStmt.setLong(6, entity.getPrice());
    }

    @Override
    public boolean save(RealEstate entity) throws SQLException {
        final String query = """
                INSERT INTO realEstate (type, address, maximum_number_of_guests, rules, image_name, price)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query)) {
            setPrepareStatement(pStmt, entity);
            return pStmt.executeUpdate() == 1;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM realEstate where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {
             pStmt.setLong(1, id);
             pStmt.execute();
        }
    }

    public List<RealEstate> findAll() throws SQLException {
        final String query = " SELECT id, address, type, maximum_number_of_guests, rules, image_name, price FROM real_estate ";


        List<RealEstate> realEstateList = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement pStmt = connection.prepareStatement(query);
            ResultSet result = pStmt.executeQuery()) {
            while (result.next()) {
                realEstateList.add(new RealEstate(
                        result.getLong("id"),
                        Housing.valueOf(result.getString("type")),
                        result.getString("address"),
                        result.getInt("maximum_number_of_guests"),
                        result.getString("rules"),
                        result.getString("image_name"),
                        result.getLong("price")));
            }
        }
        return realEstateList;
    }
}

