package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.PlanEntity;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalTime[] takingTimeArray = (LocalTime[]) resultSet.getArray("taking_time").getArray();
                List<LocalTime> takingTime = Arrays.stream(takingTimeArray).toList();
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
        } catch (SQLException e) {
            // TODO: add logging
            return null;
        }
        return null;
    }

    @Override
    public List<PlanEntity> findAll() {
        List<PlanEntity> plans = new ArrayList<>();
        final String query = "SELECT id, medication_name, dosage_mg, taking_time, duration_days, id_doctor, id_patient FROM plan_entity";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LocalTime[] takingTimeArray = (LocalTime[]) resultSet.getArray("taking_time").getArray();
                List<LocalTime> takingTime = Arrays.stream(takingTimeArray).toList();
                plans.add(
                        new PlanEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("medication_name"),
                                resultSet.getDouble("dosage_mg"),
                                takingTime,
                                resultSet.getInt("duration_days"),
                                resultSet.getLong("id_doctor"),
                                resultSet.getLong("id_patient")
                        )
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
            Array takingTime = connection.createArrayOf("LocalDate", entity.getTakingTime().toArray());
            statement.setString(1, entity.getMedicationName());
            statement.setDouble(2, entity.getDosageMg());
            statement.setArray(3, takingTime);
            statement.setInt(4, entity.getDurationDays());
            statement.setLong(5, entity.getDoctorId());
            statement.setLong(6, entity.getPatientId());
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
            Array takingTime = connection.createArrayOf("LocalDate", entity.getTakingTime().toArray());
            statement.setString(1, entity.getMedicationName());
            statement.setDouble(2, entity.getDosageMg());
            statement.setArray(3, takingTime);
            statement.setInt(4, entity.getDurationDays());
            statement.setLong(5, entity.getDoctorId());
            statement.setLong(6, entity.getPatientId());
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
}
