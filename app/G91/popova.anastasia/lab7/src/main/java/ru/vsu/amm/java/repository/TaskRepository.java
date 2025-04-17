package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class TaskRepository implements CRUDRepository<Task> {
    private static final Logger logger = Logger.getLogger(TaskRepository.class.getName());

    @Override
    public Task getByID(UUID taskID) {
        final String sql = "SELECT TaskID, ColumnID, TaskTitle, TaskDescription," +
                "StartDate, EndDate FROM tasks WHERE TaskID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, taskID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Task task = new Task();
                task.setTaskID((UUID)rs.getObject("TaskID"));
                task.setColumnID((UUID)rs.getObject("ColumnID"));
                task.setTaskTitle(rs.getString("TaskTitle"));
                Date startDate = rs.getDate("StartDate");
                if (startDate != null) task.setStartDate(startDate.toLocalDate());
                Date endDate = rs.getDate("EndDate");
                if (endDate != null) task.setEndDate(endDate.toLocalDate());
                return task;
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get task: " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        final String sql = "SELECT TaskID, ColumnID, TaskTitle, TaskDescription, StartDate, EndDate FROM tasks";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Task task = new Task();
                task.setTaskID((UUID)rs.getObject("TaskID"));
                task.setColumnID((UUID)rs.getObject("ColumnID"));
                task.setTaskTitle(rs.getString("TaskTitle"));
                Date startDate = rs.getDate("StartDate");
                if (startDate != null) task.setStartDate(startDate.toLocalDate());
                Date endDate = rs.getDate("EndDate");
                if (endDate != null) task.setEndDate(endDate.toLocalDate());
                tasks.add(task);
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get all tasks: " + e.getMessage());
        }

        return tasks;

    }

    public List<Task> getByColumn(UUID columnID) {
        List<Task> tasks = new ArrayList<>();
        final String sql = "SELECT TaskID, ColumnID, TaskTitle, TaskDescription," +
                "StartDate, EndDate FROM tasks WHERE ColumnID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, columnID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Task task = new Task();
                task.setTaskID((UUID)rs.getObject("TaskID"));
                task.setColumnID(columnID);
                task.setTaskTitle(rs.getString("TaskTitle"));
                Date startDate = rs.getDate("StartDate");
                if (startDate != null) task.setStartDate(startDate.toLocalDate());
                Date endDate = rs.getDate("EndDate");
                if (endDate != null) task.setEndDate(endDate.toLocalDate());
                tasks.add(task);
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get tasks by column: " + e.getMessage());
        }

        return tasks;

    }

    @Override
    public void save(Task task) {

        final String sql = "INSERT INTO tasks (TaskID, ColumnID, TaskTitle, TaskDescription," +
                "StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";

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

        } catch (SQLException e) {
            logger.severe("error while trying to save task: " + e.getMessage());
        }

    }

    @Override
    public void update(Task task) {
        final String sql = "UPDATE tasks SET TaskTitle = ?, TaskDescription = ?," +
                "StartDate = ?, EndDate = ?, ColumnID = ? WHERE TaskID = ?";

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

        } catch (SQLException e) {
            logger.severe("error while trying to update task: " + e.getMessage());
        }
    }

    @Override
    public void delete(UUID taskID) {

        final String sql = "DELETE FROM tasks WHERE TaskID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, taskID);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("error while trying to delete task: " + e.getMessage());
        }

    }



}
