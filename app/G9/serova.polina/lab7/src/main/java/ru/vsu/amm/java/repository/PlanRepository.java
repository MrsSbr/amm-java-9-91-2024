package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.PlanEntity;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlanRepository implements CrudRepository<PlanEntity> {

    private final DataSource dataSource;

    public PlanRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public PlanEntity findById(long id) {
        final String query = "SELECT id, medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient FROM plan_entity WHERE id = ?";
        List<PlanEntity> result = getPlans(id, query);
        if (result == null || result.isEmpty()) {
            return null;
        } else {
            return result.getFirst();
        }
    }

    public List<PlanEntity> findByDoctorId(long id) {
        final String query = "SELECT id, medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient FROM plan_entity WHERE id_doctor = ?";
        return getPlans(id, query);
    }

    public List<PlanEntity> findByPatientId(long id) {
        final String query = "SELECT id, medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient FROM plan_entity WHERE id_patient = ?";
        return getPlans(id, query);
    }

    private List<PlanEntity> getPlans(long id, String query) {
        List<PlanEntity> plans = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plans.add(
                        makePlanFromResultSet(resultSet)
                );
            }
        } catch (SQLException e) {
            // TODO: add logging
            return null;
        }
        return plans;
    }

    @Override
    public List<PlanEntity> findAll() {
        List<PlanEntity> plans = new ArrayList<>();
        final String query = "SELECT id, medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient FROM plan_entity";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                plans.add(
                        makePlanFromResultSet(resultSet)
                );
            }

        } catch (SQLException e) {
            // TODO add logging
            return null;
        }
        return plans;
    }

    @Override
    public void upsert(PlanEntity entity) {
        final String query = "INSERT INTO plan_entity (medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            prepareUpdateStatement(connection, statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    @Override
    public void update(PlanEntity entity) {
        final String query = "UPDATE plan_entity SET medication_name = ?, dosage_mg = ?, taking_time = ?, duration_days = ?, id_doctor = ?, id_patient = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            prepareUpdateStatement(connection, statement, entity);
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    @Override
    public void delete(PlanEntity entity) {
        final String query = "DELETE FROM plan_entity WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    private void prepareUpdateStatement(Connection connection, PreparedStatement statement, PlanEntity entity) throws SQLException {
        Array takingTime = connection.createArrayOf("TIME", entity.getTakingTime().stream().sorted().toArray());
        statement.setString(1, entity.getMedicationName());
        statement.setDouble(2, entity.getDosageMg());
        statement.setArray(3, takingTime);
        statement.setInt(4, entity.getDurationDays());
        statement.setLong(5, entity.getDoctorId());
        statement.setLong(6, entity.getPatientId());
    }

    private PlanEntity makePlanFromResultSet(ResultSet resultSet) throws SQLException {
        Array array = resultSet.getArray("taking_time");
        Time[] timeArray = (Time[]) array.getArray();
        List<LocalTime> takingTime = Arrays.stream(timeArray)
                .map(Time::toLocalTime)
                .toList();
        return new PlanEntity(
                resultSet.getLong("id"),
                resultSet.getString("medication_name"),
                resultSet.getDouble("dosage_mg"),
                takingTime,
                resultSet.getInt("duration_days"),
                resultSet.getLong("id_doctor"),
                resultSet.getLong("id_patient")
        );
    }
}
