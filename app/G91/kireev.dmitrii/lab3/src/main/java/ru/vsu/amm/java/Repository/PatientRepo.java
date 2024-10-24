package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Model.PatientDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.vsu.amm.java.Util.Util.getRandomPatient;

public class PatientRepo {

    private final List<PatientDTO> patients;


    public PatientRepo(List<PatientDTO> patients) {
        this.patients = patients;
    }

    public PatientRepo() {

        this.patients = new ArrayList<>();

        for (int i = 0; i < PatientConfig.COUNT; i++) {

            patients.add(getRandomPatient());

        }
    }

    public List<PatientDTO> getAll() {

        return patients;
    }

    public List<PatientDTO> findByIsHealthy(LocalDate healthyPeriod) {

        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isAfter(healthyPeriod)).toList();
    }

    public List<PatientDTO> findByMightBeHealthy(LocalDate healthyPeriod) {

        var rofl = patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct().toList();
        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isBefore(healthyPeriod)).toList();
    }

    public List<PatientDTO> findByDateAfter(LocalDate date) {

        return patients.stream().filter(x -> x.date().isAfter(date)).distinct().toList();
    }

    public List<PatientDTO> findByDateBetween(LocalDate after, LocalDate before) {
        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(x -> x.date().isAfter(after) && x.date().isBefore(before)).toList();
    }
}
