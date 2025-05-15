package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.TaskEntity;
import ru.vsu.amm.java.enums.TaskStatus;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepo implements CrudRepo<TaskEntity> {
    private final DataSource dataSource;

    public TaskRepo() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<TaskEntity> getById(int id) throws SQLException {
        final String query = "SELECT task_id, employee_id, hotel_room_id, manager_id, status, description, created_at, updated_at FROM task WHERE task_id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(configureTaskEntityFromResultSet(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<TaskEntity> getAll() throws SQLException {
        final String query = "SELECT task_id, employee_od, hotel_room_id, manager_id, status, description, created_at, updated_at FROM task";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<TaskEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = configureTaskEntityFromResultSet(resultSet);
            entityList.add(entity);
        }

        return entityList;
    }

    public List<TaskEntity> getAllByEmployeeId(int employeeId) throws SQLException {
        final String query = """
                SELECT task_id, employee_id, hotel_room_id, manager_id, status, description, created_at, updated_at
                FROM task
                WHERE employee_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, employeeId);
        preparedStatement.execute();

        List<TaskEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = configureTaskEntityFromResultSet(resultSet);
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void update(TaskEntity entity) throws SQLException {
        final String query = """
                UPDATE task SET employee_id = ?, hotel_room_id = ?, manager_id = ?, status = ?, description = ?, created_at = ?, updated_at = ?
                WHERE task_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(8, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public TaskEntity save(TaskEntity entity) throws SQLException {
        final String query = """
                INSERT INTO task (employee_id, hotel_room_id, manager_id, status, description, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING task_id""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            entity.setId(resultSet.getInt(1));
            return entity;
        }

        throw new SQLException("Save error");
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM task WHERE task_id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, TaskEntity entity) throws SQLException {
        preparedStatement.setInt(1, entity.getEmployeeId());
        preparedStatement.setInt(2, entity.getHotelRoomId());
        if (entity.getManagerId() == null) {
            preparedStatement.setNull(3, Types.INTEGER);
        } else {
            preparedStatement.setInt(3, entity.getManagerId());
        }
        preparedStatement.setString(4, entity.getStatus().name());
        if (entity.getDescription() == null) {
            preparedStatement.setNull(5, Types.VARCHAR);
        } else {
            preparedStatement.setString(5, entity.getDescription());
        }
        preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getCreatedAt()));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(entity.getUpdatedAt()));
    }

    private TaskEntity configureTaskEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new TaskEntity(
                resultSet.getInt("task_id"),
                resultSet.getInt("employee_id"),
                resultSet.getInt("hotel_room_id"),
                resultSet.getObject("manager_id", Integer.class),
                TaskStatus.valueOf(resultSet.getString("status")),
                resultSet.getString("description"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
