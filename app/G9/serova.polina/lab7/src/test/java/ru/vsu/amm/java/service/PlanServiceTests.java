package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.util.UserNameFormatter;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlanServiceTests {

    private PlanService planService;
    private PlanRepository planRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        planRepository = mock(PlanRepository.class);
        userRepository = mock(UserRepository.class);

        planService = new PlanService(planRepository, userRepository);
    }

    @Test
    void addPlan_shouldCallRepositoryUpsert() {
        List<LocalTime> takingTime = List.of(LocalTime.of(8, 0));

        planService.addPlan("Aspirin", 100.0, takingTime, 7, 1L, 2L);

        verify(planRepository).upsert(any(PlanEntity.class));
    }

    @Test
    void getPlanById_shouldReturnPlanWhenExists() {
        PlanEntity testPlan = createTestPlan(1L);
        when(planRepository.findById(1L)).thenReturn(testPlan);

        PlanEntity result = planService.getPlanById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void updatePlan_shouldCallRepositoryUpdate() {
        List<LocalTime> takingTime = List.of(LocalTime.of(8, 0));

        planService.updatePlan(1L, "Aspirin", 100.0, takingTime, 7, 1L, 2L);

        verify(planRepository).update(any(PlanEntity.class));
    }

    @Test
    void deletePlanById_shouldCallRepositoryDeleteWhenPlanExists() {
        PlanEntity testPlan = createTestPlan(1L);
        when(planRepository.findById(1L)).thenReturn(testPlan);

        planService.deletePlanById(1L);

        verify(planRepository).delete(testPlan);
    }

    @Test
    void getDoctorPlans_shouldReturnEmptyListWhenNoPlans() {
        when(planRepository.findByDoctorId(1L)).thenReturn(Collections.emptyList());

        List<PlanViewDto> result = planService.getDoctorPlans(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPatientPlans_shouldReturnPlanViewDtos() {
        PlanEntity testPlan = createTestPlan(1L);
        UserEntity doctor = createTestUser(1L, "Doctor");
        UserEntity patient = createTestUser(2L, "Patient");

        when(planRepository.findByPatientId(2L)).thenReturn(List.of(testPlan));
        when(userRepository.findById(1L)).thenReturn(doctor);
        when(userRepository.findById(2L)).thenReturn(patient);

        try (MockedStatic<UserNameFormatter> mockedFormatter = mockStatic(UserNameFormatter.class)) {
            mockedFormatter.when(() -> UserNameFormatter.formatName(doctor))
                    .thenReturn("Dr. Doctor");
            mockedFormatter.when(() -> UserNameFormatter.formatName(patient))
                    .thenReturn("Mr. Patient");

            List<PlanViewDto> result = planService.getPatientPlans(2L);

            assertEquals(1, result.size());
            assertEquals("Dr. Doctor", result.getFirst().doctorFullName());
        }
    }

    private PlanEntity createTestPlan(Long id) {
        PlanEntity plan = new PlanEntity();
        plan.setId(id);
        plan.setMedicationName("TestMed");
        plan.setDosageMg(50.0);
        plan.setTakingTime(List.of(LocalTime.of(12, 0)));
        plan.setDurationDays(7);
        plan.setDoctorId(1L);
        plan.setPatientId(2L);
        return plan;
    }

    private UserEntity createTestUser(Long id, String name) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setSurname("Test");
        return user;
    }
}