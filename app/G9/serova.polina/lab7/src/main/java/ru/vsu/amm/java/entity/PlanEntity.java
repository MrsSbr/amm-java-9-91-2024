package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class PlanEntity {
    private long id;
    private String medicationName;
    private double dosageMg;
    private List<LocalTime> takingTime;
    private int durationDays;
    private long patientId;
    private long doctorId;
}
