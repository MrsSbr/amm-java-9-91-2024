package ru.vsu.amm.java.repository;

import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ColumnRepository implements CRUDRepository<Column> {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ColumnRepository.class);

    private static Column mapResultSetToColumn(ResultSet rs) throws SQLException {
        Column column = new Column();
        column.setColumnID((UUID)rs.getObject("ColumnID"));
        column.setBoardID((UUID)rs.getObject("BoardID"));
        column.setColumnTitle(rs.getString("ColumnTitle"));
        return column;
    }

    @Override
    public Column getByID(UUID columnID) {
        final String sql = "SELECT \"ColumnID\", \"BoardID\", \"ColumnTitle\" FROM columns WHERE \"ColumnID\" = ?";
        log.debug("Fetching column with ID: {}", columnID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, columnID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToColumn(rs);
            }
            log.info("Successfully fetched column: {}", columnID);
        } catch (SQLException e) {
            log.error("Failed to get column: {}", columnID + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Column> getAll() {
        List<Column> columns = new ArrayList<>();
        final String sql = "SELECT \"ColumnID\", \"ColumnTitle\" FROM columns";
        log.debug("Fetching all columns");

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                columns.add(mapResultSetToColumn(rs));
            }
            log.info("Successfully fetched {} columns", columns.size());
        } catch (SQLException e) {
            log.error("Failed to get all columns: {}", e.getMessage());
        }
        return columns;
    }

    @Override
    public void save(Column column) {
        final String sql = "INSERT INTO columns (\"ColumnID\", \"BoardID\", \"ColumnTitle\") VALUES (?, ?, ?)";
        log.debug("Saving column: {}", column.getColumnID());

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, column.getColumnID());
            ps.setObject(2, column.getBoardID());
            ps.setObject(3, column.getColumnTitle());
            ps.executeUpdate();
            log.info("Column saved successfully: {}", column.getColumnID());
        } catch (SQLException e) {
            log.error("Failed to save column: {}", column.getColumnID() + e.getMessage());
        }
    }

    @Override
    public void update(Column column) {
        final String sql = "UPDATE columns SET \"ColumnTitle\" = ? WHERE \"ColumnID\" = ?";
        log.debug("Updating column: {}", column.getColumnID());

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, column.getColumnTitle());
            ps.setObject(2, column.getColumnID());
            ps.executeUpdate();
            log.info("Column updated successfully: {}", column.getColumnID());
        } catch (SQLException e) {
            log.error("Failed to update column: {}", column.getColumnID() + e.getMessage());
        }
    }

    @Override
    public void delete(UUID columnID) {
        final String sql = "DELETE FROM columns WHERE \"ColumnID\" = ?";
        log.debug("Deleting column: {}", columnID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, columnID);
            ps.executeUpdate();
            log.info("Column deleted successfully: {}", columnID);
        } catch (SQLException e) {
            log.error("Failed to delete column: {}", columnID + e.getMessage());
        }
    }

    public List<Column> getColumnsByBoardId(UUID boardId) {
        final String sql = "SELECT \"ColumnID\", \"BoardID\", \"ColumnTitle\" FROM columns WHERE \"BoardID\" = ?";
        log.debug("Fetching columns for board: {}", boardId);
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, boardId);
            ResultSet rs = ps.executeQuery();
            List<Column> columns = new ArrayList<>();
            while (rs.next()) {
                columns.add(mapResultSetToColumn(rs));
            }
            log.info("Successfully fetched {} columns for board: {}", columns.size(), boardId);
            return columns;
        } catch (SQLException e) {
            log.error("Failed to fetch columns for board: {}", boardId, e);
            throw new RuntimeException("Failed to fetch columns for board", e);
        }
    }

    public void deleteColumnsByBoardId(UUID boardId) {
        final String sql = "DELETE FROM columns WHERE \"BoardID\" = ?";
        log.debug("Deleting columns for board: {}", boardId);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, boardId);
            int deleted = ps.executeUpdate();
            log.info("Successfully deleted {} columns for board: {}", deleted, boardId);
        } catch (SQLException e) {
            log.error("Failed to delete columns for board: {}", boardId + e.getMessage());
            throw new RuntimeException("Failed to delete columns for board", e);
        }
    }
}
