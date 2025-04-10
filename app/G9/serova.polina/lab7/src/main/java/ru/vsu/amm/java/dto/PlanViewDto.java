package ru.vsu.amm.java.dto;

import ru.vsu.amm.java.entity.PlanEntity;

public record PlanViewDto(PlanEntity plan, String doctorFullName, String patientFullName) {}
