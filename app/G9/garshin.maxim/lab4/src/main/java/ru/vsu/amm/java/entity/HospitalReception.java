package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Specialization;

import java.time.LocalDate;

public record HospitalReception(LocalDate date, String doctorFullName, Specialization specialization, String patientFullName, int cost) {
}