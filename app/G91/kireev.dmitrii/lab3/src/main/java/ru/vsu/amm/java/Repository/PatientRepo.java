package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Config.PatientConfig;
import ru.vsu.amm.java.Model.Patient;

import java.util.ArrayList;
import java.util.List;

import static ru.vsu.amm.java.Util.Util.getRandomPatient;

public class PatientRepo {

    private final List<Patient> patients;


    public PatientRepo(List<Patient> patients) {
        this.patients = patients;
    }

    public PatientRepo() {

        this.patients = new ArrayList<>();

        for (int i = 0; i < PatientConfig.COUNT; i++) {

            patients.add(getRandomPatient());

        }
    }

    public List<Patient> getAll() {

        return patients;
    }

}
