package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Model.PatientDTO;
import ru.vsu.amm.java.Repository.PatientRepo;

import java.util.List;

public class PatientService {

    private final PatientRepo patientRepo;

    public PatientService() {
        patientRepo = new PatientRepo();
    }

    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public List<PatientDTO> findAll() {
        return patientRepo.getAll();
    }

    public List<PatientDTO> findByIsHealthy() {
        return patientRepo.findByIsHealthy(PatientConfig.HEALTHY_PERIOD);
    }

    public List<PatientDTO> findByMightBeHealthy() {
        return patientRepo.findByMightBeHealthy(PatientConfig.HEALTHY_PERIOD);
    }

    public List<PatientDTO> findByDateAfter() {
        return patientRepo.findByDateAfter(PatientConfig.TASK2_PERIOD);
    }

    public List<PatientDTO> findByDateBetween() {
        return patientRepo.findByDateBetween(PatientConfig.TASK3_AFTER, PatientConfig.TASK3_BEFORE);
    }

}
