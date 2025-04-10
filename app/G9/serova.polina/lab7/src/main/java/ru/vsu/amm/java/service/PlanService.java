package ru.vsu.amm.java.service;

import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public PlanService() {
        this.planRepository = new PlanRepository();
        this.userRepository = new UserRepository();
    }

    public List<PlanViewDto> getDoctorPlans(long userId) {
        List<PlanEntity> plans = planRepository.findByDoctorId(userId);

        return getPlanViewDtos(plans);
    }

    public List<PlanViewDto> getPatientPlans(long userId) {
        List<PlanEntity> plans = planRepository.findByPatientId(userId);

        return getPlanViewDtos(plans);
    }

    private List<PlanViewDto> getPlanViewDtos(List<PlanEntity> plans) {
        return plans.stream()
                .map(plan -> {
                    UserEntity doctor = userRepository.findById(plan.getDoctorId());
                    UserEntity patient = userRepository.findById(plan.getPatientId());
                    String doctorFullName = formatName(doctor);
                    String patientFullName = formatName(patient);
                    return new PlanViewDto(plan, doctorFullName, patientFullName);
                })
                .collect(Collectors.toList());
    }
}