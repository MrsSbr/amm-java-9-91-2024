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
                "StartDate, EndDate FROM columns WHERE ColumnID = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, taskID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Task task = new Task();
                task.setTaskID((UUID)rs.getObject("TaskID"));
                task.setColumnID((UUID)rs.getObject("ColumnID"));
                task.setTaskTitle(rs.getString("TaskTitle"));
                task.setStartDate(rs.getDate("StartDate").toLocalDate());
                task.setEndDate(rs.getDate("EndDate").toLocalDate());
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
        final String sql = "SELECT TaskID, ColumnID, TaskTitle, TaskDescription, StartDate, EndDate FROM columns";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Task task = new Task();
                task.setTaskID((UUID)rs.getObject("TaskID"));
                task.setColumnID((UUID)rs.getObject("ColumnID"));
                task.setTaskTitle(rs.getString("TaskTitle"));
                task.setStartDate(rs.getDate("StartDate").toLocalDate());
                task.setEndDate(rs.getDate("EndDate").toLocalDate());
                tasks.add(task);
            }

        } catch (SQLException e) {
            logger.severe("error while trying to get all taskss: " + e.getMessage());
        }

        return tasks;

    }

    @Override
    public void save(Task task) {

        final String sql = "INSERT INTO tasks (TaskID, ColumnID, TaskTitle, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, task.getColumnID());
            ps.setObject(2, task.getTaskID());
            ps.setObject(3, task.getTaskTitle());
            ps.setObject(4, task.getStartDate());
            ps.setObject(5, task.getEndDate());
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
