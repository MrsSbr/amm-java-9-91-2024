package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;
import java.time.LocalTime;
import java.util.List;
import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

public class PlanService {
    private static final Logger logger = LoggerFactory.getLogger(PlanService.class);
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public PlanService() {
        this.planRepository = new PlanRepository();
        this.userRepository = new UserRepository();
        logger.info("Сервис планов лечения инициализирован");
    }

    public PlanService(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        logger.info("Сервис планов лечения инициализирован с заданными репозиториями");
    }

    public void addPlan(
            String medicationName,
            double dosageMg,
            List<LocalTime> takingTime,
            int durationDays,
            long doctorId,
            long patientId
    ) {
        PlanEntity plan = new PlanEntity();
        plan.setMedicationName(medicationName);
        plan.setDosageMg(dosageMg);
        plan.setTakingTime(takingTime);
        plan.setDurationDays(durationDays);
        plan.setDoctorId(doctorId);
        plan.setPatientId(patientId);

        planRepository.upsert(plan);
        logger.info("Добавлен новый план лечения: препарат '{}', дозировка {} мг, врач ID {}, пациент ID {}",
                medicationName, dosageMg, doctorId, patientId);
    }

    public PlanEntity getPlanById(long planId) {
        logger.debug("Запрос плана лечения по ID: {}", planId);
        PlanEntity plan = planRepository.findById(planId);
        if (plan == null) {
            logger.warn("План лечения с ID {} не найден", planId);
        }
        return plan;
    }

    public void updatePlan(
            long planId,
            String medicationName,
            double dosageMg,
            List<LocalTime> takingTime,
            int durationDays,
            long doctorId,
            long patientId
    ) {
        PlanEntity plan = new PlanEntity(
                planId,
                medicationName,
                dosageMg,
                takingTime,
                durationDays,
                doctorId,
                patientId
        );

        planRepository.update(plan);
        logger.info("Обновлен план лечения ID {}: препарат '{}', дозировка {} мг",
                planId, medicationName, dosageMg);
    }

    public void deletePlanById(long planId) {
        logger.debug("Попытка удаления плана лечения с ID: {}", planId);
        PlanEntity plan = planRepository.findById(planId);
        if (plan != null) {
            planRepository.delete(plan);
            logger.info("Удален план лечения с ID: {}", planId);
        } else {
            logger.warn("Попытка удаления несуществующего плана лечения с ID: {}", planId);
        }
    }

    public List<PlanViewDto> getDoctorPlans(long userId) {
        logger.debug("Запрос планов лечения для врача с ID: {}", userId);
        List<PlanEntity> plans = planRepository.findByDoctorId(userId);

        if (plans == null || plans.isEmpty()) {
            logger.debug("Планы лечения для врача ID {} не найдены", userId);
            return List.of();
        }

        return getPlanViewDtos(plans);
    }

    public List<PlanViewDto> getPatientPlans(long userId) {
        logger.debug("Запрос планов лечения для пациента с ID: {}", userId);
        List<PlanEntity> plans = planRepository.findByPatientId(userId);

        if (plans == null || plans.isEmpty()) {
            logger.debug("Планы лечения для пациента ID {} не найдены", userId);
            return List.of();
        }

        return getPlanViewDtos(plans);
    }

    private List<PlanViewDto> getPlanViewDtos(List<PlanEntity> plans) {
        return plans.stream()
                .map(plan -> {
                    UserEntity doctor = userRepository.findById(plan.getDoctorId());
                    UserEntity patient = userRepository.findById(plan.getPatientId());

                    if (doctor == null || patient == null) {
                        logger.warn("Не найдены пользователи для плана лечения ID {} (врач ID {}, пациент ID {})",
                                plan.getId(), plan.getDoctorId(), plan.getPatientId());
                    }

                    String doctorFullName = formatName(doctor);
                    String patientFullName = formatName(patient);
                    return new PlanViewDto(plan, doctorFullName, patientFullName);
                })
                .toList();
    }
}