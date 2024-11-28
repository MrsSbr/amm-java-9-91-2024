package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Model.Patient;

import java.time.LocalDate;
import java.util.List;

public class PatientService {

    public List<Patient> findByIsHealthy(List<Patient> patients) {
        LocalDate date = LocalDate.now().minusYears(1);
        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isAfter(date))
                .toList();
    }

    public List<Patient> findByMightBeHealthy(List<Patient> patients) {
        LocalDate date = LocalDate.now().minusYears(1);

        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isBefore(date))
                .toList();
    }

    public List<Patient> findByDateAfter(List<Patient> patients) {
        LocalDate date = LocalDate.now().minusYears(3);
        return patients.stream().filter(x -> x.date().isAfter(date)).distinct()
                .toList();
    }

    public List<Patient> findByDateBetween(List<Patient> patients) {
        LocalDate from = LocalDate.now().minusYears(5);
        LocalDate to = LocalDate.now().minusYears(2);

        return patients.stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(x -> x.date().isAfter(from) &&
                        x.date().isBefore(to))
                .toList();
    }

}
