package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;


class PlanRepositoryTests {

    private PlanRepository planRepository;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        try (MockedStatic<DatabaseConfiguration> mocked = mockStatic(DatabaseConfiguration.class)) {
            mocked.when(DatabaseConfiguration::getDataSource).thenReturn(mockDataSource);
            planRepository = new PlanRepository();
        }
    }

    @Test
    void findById_shouldReturnPlanWhenExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        mockPlanResultSet(mockResultSet, 1L, "Medication", 50.0,
                new Time[]{Time.valueOf("08:00:00")}, 7, 1L, 2L);

        PlanEntity result = planRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(mockStatement).setLong(1, 1L);
    }

    @Test
    void findById_shouldReturnNullWhenNotExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        PlanEntity result = planRepository.findById(1L);

        assertNull(result);
    }

    @Test
    void findByDoctorId_shouldReturnPlansList() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        mockPlanResultSet(mockResultSet, 1L, "Med1", 50.0,
                new Time[]{Time.valueOf("08:00:00")}, 7, 1L, 2L);
        mockPlanResultSet(mockResultSet, 2L, "Med2", 100.0,
                new Time[]{Time.valueOf("12:00:00")}, 14, 1L, 3L);

        List<PlanEntity> result = planRepository.findByDoctorId(1L);

        assertEquals(2, result.size());
        verify(mockStatement).setLong(1, 1L);
    }

    @Test
    void upsert_shouldExecuteInsert() throws SQLException {
        PlanEntity plan = createTestPlan();

        planRepository.upsert(plan);

        verify(mockConnection).prepareStatement(contains("INSERT INTO"));
        verify(mockStatement).executeUpdate();
    }

    @Test
    void update_shouldExecuteUpdate() throws SQLException {
        PlanEntity plan = createTestPlan();
        plan.setId(1L);

        planRepository.update(plan);

        verify(mockConnection).prepareStatement(contains("UPDATE"));
        verify(mockStatement).setLong(7, 1L);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void delete_shouldExecuteDelete() throws SQLException {
        PlanEntity plan = createTestPlan();
        plan.setId(1L);

        planRepository.delete(plan);

        verify(mockConnection).prepareStatement(contains("DELETE"));
        verify(mockStatement).setLong(1, 1L);
        verify(mockStatement).executeUpdate();
    }

    private void mockPlanResultSet(ResultSet rs, long id, String medName, double dosage,
                                   Time[] times, int days, long doctorId, long patientId) throws SQLException {
        Array mockArray = mock(Array.class);
        when(mockArray.getArray()).thenReturn(times);

        when(rs.getLong("id")).thenReturn(id);
        when(rs.getString("medication_name")).thenReturn(medName);
        when(rs.getDouble("dosage_mg")).thenReturn(dosage);
        when(rs.getArray("taking_time")).thenReturn(mockArray);
        when(rs.getInt("duration_days")).thenReturn(days);
        when(rs.getLong("id_doctor")).thenReturn(doctorId);
        when(rs.getLong("id_patient")).thenReturn(patientId);
    }

    private PlanEntity createTestPlan() {
        PlanEntity plan = new PlanEntity();
        plan.setMedicationName("TestMed");
        plan.setDosageMg(50.0);
        plan.setTakingTime(List.of(LocalTime.of(8, 0)));
        plan.setDurationDays(7);
        plan.setDoctorId(1L);
        plan.setPatientId(2L);

        return plan;
    }
}