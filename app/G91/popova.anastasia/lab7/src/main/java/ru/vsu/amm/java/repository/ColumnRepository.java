package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ColumnRepository implements CRUDRepository<Column> {
    private static final Logger logger = Logger.getLogger(ColumnRepository.class.getName());

    @Override
    public Column getByID(UUID columnID) {
        final String sql = "SELECT ColumnID, BoardID, ColumnTitle FROM columns WHERE ColumnID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, columnID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Column column = new Column();
                column.setColumnID((UUID)rs.getObject("ColumnID"));
                column.setBoardID((UUID)rs.getObject("BoardID"));
                column.setColumnTitle(rs.getString("ColumnTitle"));
                return column;
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get column: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Column> getAll() {
        List<Column> columns = new ArrayList<>();
        final String sql = "SELECT ColumnID, ColumnTitle FROM columns";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Column column = new Column();
                column.setColumnID((UUID)rs.getObject("ColumnID"));
                column.setBoardID((UUID)rs.getObject("BoardID"));
                column.setColumnTitle(rs.getString("ColumnTitle"));
                columns.add(column);
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get all columns: " + e.getMessage());
        }

        return columns;
    }

    @Override
    public void save(Column column) {
        final String sql = "INSERT INTO columns (ColumnID, BoardID, ColumnTitle) VALUES (?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, column.getColumnID());
            ps.setObject(2, column.getBoardID());
            ps.setObject(3, column.getColumnTitle());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to save column: " + e.getMessage());
        }

    }

    @Override
    public void update(Column column) {
        final String sql = "UPDATE columns SET ColumnTitle = ? WHERE ColumnID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, column.getColumnTitle());
            ps.setObject(2, column.getColumnID());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to update column: " + e.getMessage());
        }

    }

    @Override
    public void delete(UUID columnID) {
        final String sql = "DELETE FROM columns WHERE ColumnID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, columnID);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to delete column: " + e.getMessage());
        }

    }

}
