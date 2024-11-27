package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Model.Patient;
import ru.vsu.amm.java.Repository.PatientRepo;

import java.time.LocalDate;
import java.util.List;

public class PatientService {

    private final PatientRepo patientRepo;

    public PatientService() {
        patientRepo = new PatientRepo();
    }

    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public List<Patient> findAll() {
        return patientRepo.getAll();
    }


    public List<Patient> findByIsHealthy() {

        return patientRepo.getAll().stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isAfter(LocalDate.now().minusYears(1)))
                .toList();
    }

    public List<Patient> findByMightBeHealthy() {
        return patientRepo.getAll().stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(p -> p.isHealthy() && p.date().isBefore(LocalDate.now().minusYears(1)))
                .toList();
    }

    public List<Patient> findByDateAfter() {

        return patientRepo.getAll().stream().filter(x -> x.date().isAfter(LocalDate.now().minusYears(3))).distinct()
                .toList();
    }

    public List<Patient> findByDateBetween() {
        return patientRepo.getAll().stream()
                .sorted((p1, p2) -> p2.date().compareTo(p1.date()))
                .distinct()
                .filter(x -> x.date().isAfter(LocalDate.now().minusYears(5)) &&
                        x.date().isBefore(LocalDate.now().minusYears(2)))
                .toList();
    }

}
