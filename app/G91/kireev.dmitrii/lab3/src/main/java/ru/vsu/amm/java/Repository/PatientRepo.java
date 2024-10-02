package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Model.PatientDTO;
import ru.vsu.amm.java.Util.Util;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PatientRepo {

    private final List<PatientDTO> patients;


    public PatientRepo(List<PatientDTO> patients) {
        this.patients = patients;
    }

    public PatientRepo() {

        this.patients = new LinkedList<>();

        for (int i = 0; i < PatientConfig.COUNT; i++) {

            patients.add(
                    new PatientDTO(
                            Util.getRandomString(PatientConfig.NAMELENGTH),
                            Util.getRandomString(PatientConfig.SURNAMELENGTH),
                            Util.getRandomString(PatientConfig.PATRONYMICLENGTH),
                            Util.getRandomBoolean(),
                            Util.getRandomString(PatientConfig.ILLNESSLENGTH),
                            Util.getRandomDate())
            );
        }
    }

    public List<PatientDTO> getAll() {

        return patients;
    }

    public List<PatientDTO> findByIsHealthyAfterDate(LocalDate healthyPeriod) {

        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isAfter(healthyPeriod)).toList();
    }

    public List<PatientDTO> findByIsHealthy() {

        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(PatientDTO::isHealthy).toList();
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
