package ru.vsu.amm.java;

import java.time.LocalDate;

public class DoctorAppointment {
    private final LocalDate date;
    private final String doctorFullName;
    private final Specialization specialization;
    private final String patientFullName;
    private final int cost;

    public DoctorAppointment(LocalDate date, String doctorFullName, Specialization specialization, String patientFullName, int cost) {
        this.date = date;
        this.doctorFullName = doctorFullName;
        this.specialization = specialization;
        this.patientFullName = patientFullName;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Приём у врача {" +
                "дата=" + date +
                ", врач='" + doctorFullName + '\'' +
                ", специализация='" + specialization + '\'' +
                ", пациент='" + patientFullName + '\'' +
                ", стоимость=" + cost +
                '}';
    }
}