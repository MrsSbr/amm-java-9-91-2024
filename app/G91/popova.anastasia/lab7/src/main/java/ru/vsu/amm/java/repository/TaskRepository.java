package ru.vsu.amm.java.repository;

import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements CRUDRepository<Task> {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TaskRepository.class);

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setTaskID((UUID)rs.getObject("TaskID"));
        task.setColumnID((UUID)rs.getObject("ColumnID"));
        task.setTaskTitle(rs.getString("TaskTitle"));
        task.setTaskDescription(rs.getString("TaskDescription"));
        Date startDate = rs.getDate("StartDate");
        if (startDate != null) task.setStartDate(startDate.toLocalDate());
        Date endDate = rs.getDate("EndDate");
        if (endDate != null) task.setEndDate(endDate.toLocalDate());
        return task;
    }

    @Override
    public Task getByID(UUID taskID) {
        final String sql = "SELECT \"TaskID\", \"ColumnID\", \"TaskTitle\", \"TaskDescription\"," +
                "\"StartDate\", \"EndDate\" FROM tasks WHERE \"TaskID\" = ?";
        log.debug("Fetching task with ID: {}", taskID);

        try (Connection c = DatabaseConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setObject(1, taskID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return mapResultSetToTask(rs);
                }
                log.info("Successfully fetched task: {}", taskID);
        } catch (SQLException e) {
            log.error("Failed to fetch task: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        final String sql = "SELECT \"TaskID\", \"ColumnID\", \"TaskTitle\", \"TaskDescription\"," +
                "\"StartDate\", \"EndDate\" FROM tasks";
        log.debug("Fetching all tasks");

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
            log.info("Successfully fetched {} tasks", tasks.size());
        } catch (SQLException e) {
            log.error("Failed to fetch all tasks: {}", e.getMessage());
        }
        return tasks;
    }

    public List<Task> getByColumn(UUID columnID) {
        List<Task> tasks = new ArrayList<>();
        final String sql = "SELECT \"TaskID\", \"ColumnID\", \"TaskTitle\", \"TaskDescription\"," +
                "\"StartDate\", \"EndDate\" FROM tasks WHERE \"ColumnID\" = ?";
        log.debug("Fetching tasks for column: {}", columnID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, columnID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
            log.info("Successfully fetched {} tasks for column: {}", tasks.size(), columnID);
        } catch (SQLException e) {
            log.error("Failed to fetch tasks for column: {}", columnID + e.getMessage());
        }
        return tasks;
    }

    @Override
    public void save(Task task) {
        final String sql = "INSERT INTO tasks (\"TaskID\", \"ColumnID\", \"TaskTitle\", \"TaskDescription\"," +
                "\"StartDate\", \"EndDate\") VALUES (?, ?, ?, ?, ?, ?)";
        log.debug("Saving task: {}", task.getTaskID());

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, task.getTaskID());
            ps.setObject(2, task.getColumnID());
            ps.setObject(3, task.getTaskTitle());
            if (task.getTaskDescription() != null) {
                ps.setString(4, task.getTaskDescription());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            if (task.getStartDate() != null) {
                ps.setDate(5, Date.valueOf(task.getStartDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            if (task.getEndDate() != null) {
                ps.setDate(6, Date.valueOf(task.getEndDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.executeUpdate();
            log.info("Task saved successfully: {}", task.getTaskID());
        } catch (SQLException e) {
            log.error("Failed to save task: {}", task.getTaskID() + e.getMessage());
        }

    }

    @Override
    public void update(Task task) {
        final String sql = "UPDATE tasks SET \"TaskTitle\" = ?, \"TaskDescription\" = ?," +
                "\"StartDate\" = ?, \"EndDate\" = ?, \"ColumnID\" = ? WHERE \"TaskID\" = ?";
        log.debug("Updating task: {}", task.getTaskID());

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, task.getTaskTitle());
            if (task.getTaskDescription() != null) {
                ps.setString(2, task.getTaskDescription());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setObject(3, task.getStartDate());
            ps.setObject(4, task.getEndDate());
            ps.setObject(5, task.getColumnID());
            ps.setObject(6, task.getTaskID());
            ps.executeUpdate();
            log.info("Task updated successfully: {}", task.getTaskID());
        } catch (SQLException e) {
            log.error("Failed to update task: {}", task.getTaskID() + e.getMessage());
        }
    }

    @Override
    public void delete(UUID taskID) {
        final String sql = "DELETE FROM tasks WHERE \"TaskID\" = ?";
        log.debug("Deleting task: {}", taskID);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, taskID);
            ps.executeUpdate();
            log.info("Task deleted successfully: {}", taskID);
        } catch (SQLException e) {
            log.error("Failed to delete task: {}", taskID + e.getMessage());
        }
    }

    public static void deleteTasksByColumnId(UUID columnId) {
        final String sql = "DELETE FROM tasks WHERE \"ColumnID\" = ?";
        log.debug("Deleting tasks for column: {}", columnId);

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, columnId);
            int rowsAffected = ps.executeUpdate();
            log.info("Successfully deleted {} tasks for column: {}", rowsAffected, columnId);
        } catch (SQLException e) {
            log.error("Failed to delete tasks for column: {}", columnId + e.getMessage());
        }
    }

}
