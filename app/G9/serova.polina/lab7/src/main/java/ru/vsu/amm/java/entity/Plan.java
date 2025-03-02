package ru.vsu.amm.java.entity;

import java.time.LocalTime;
import java.util.List;

public class Plan {
    private long id;
    private String medicationName;
    private double dosageMg;
    private List<LocalTime> takingTime;
    private int durationDays;
    private int patientId;
    private User patient;
    private int doctorId;
    private User doctor;
}
