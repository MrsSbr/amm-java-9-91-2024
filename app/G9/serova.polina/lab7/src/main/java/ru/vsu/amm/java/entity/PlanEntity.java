package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanEntity {
    private long id;
    private String medicationName;
    private double dosageMg;
    private List<LocalTime> takingTime;
    private int durationDays;
    private long doctorId;
    private long patientId;
}
