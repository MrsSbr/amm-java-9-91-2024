package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanRepositoryIntegrationTests {

    private PlanRepository planRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        planRepository = new PlanRepository();
        userRepository = new UserRepository();
        clearPlansTable();
    }

    @AfterEach
    void tearDown() {
        clearPlansTable();
    }

    @Test
    void crudOperations_shouldWorkCorrectly() {
        createUsers(2);
        createTestPlan(1L, 2L);
        PlanEntity plan = planRepository.findAll().getFirst();

        PlanEntity found = planRepository.findById(plan.getId());
        assertEquals("Аспирин", found.getMedicationName());
        assertEquals(2, found.getTakingTime().size());

        plan.setMedicationName("Аспирин+");
        plan.setDosageMg(200.0);
        planRepository.update(plan);

        PlanEntity updated = planRepository.findById(plan.getId());
        assertEquals("Аспирин+", updated.getMedicationName());
        assertEquals(200.0, updated.getDosageMg());

        // Delete
        planRepository.delete(plan);
        assertNull(planRepository.findById(plan.getId()));
    }

    @Test
    void findByDoctorId_shouldReturnCorrectPlans() {
        createUsers(3);
        createTestPlan(1L, 1L);
        createTestPlan(1L, 2L);
        createTestPlan(3L, 2L);

        List<PlanEntity> plans = planRepository.findByDoctorId(1L);
        assertEquals(2, plans.size());
        assertTrue(plans.stream().allMatch(p -> p.getDoctorId() == 1L));
    }

    @Test
    void findByPatientId_shouldReturnCorrectPlans() {
        createUsers(2);
        createTestPlan(1L, 1L);
        createTestPlan(1L, 2L);
        createTestPlan(2L, 1L);

        List<PlanEntity> plans = planRepository.findByPatientId(1L);
        assertEquals(2, plans.size());
        assertTrue(plans.stream().allMatch(p -> p.getPatientId() == 1L));
    }

    private void createTestPlan(long doctorId, long patientId) {
        PlanEntity plan = new PlanEntity();
        plan.setMedicationName("Аспирин");
        plan.setDosageMg(50.0);
        plan.setTakingTime(List.of(LocalTime.of(12, 0), LocalTime.of(18, 0)));
        plan.setDurationDays(7);
        plan.setDoctorId(doctorId);
        plan.setPatientId(patientId);
        planRepository.upsert(plan);
    }

    private void createUsers(long count) {
        for (int i = 0; i < count; i++) {
            UserEntity user = new UserEntity();
            user.setName("Test" + i);
            user.setSurname("Test" + i);
            user.setBirthday(LocalDate.now());
            user.setEmail("test" + i);
            user.setPasswordHash("test" + i);
            user.setRole(Role.DOCTOR);
            userRepository.upsert(user);
        }
    }

    private void clearPlansTable() {
        planRepository.findAll().forEach(planRepository::delete);
    }
}