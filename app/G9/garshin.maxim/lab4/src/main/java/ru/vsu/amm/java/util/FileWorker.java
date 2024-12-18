package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.enums.Specialization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public final class FileWorker {
    private static final int DATE_INDEX = 0;
    private static final int DOCTOR_FULL_NAME_INDEX = 1;
    private static final int SPECIALIZATION_INDEX = 2;
    private static final int PATIENT_FULL_NAME_INDEX = 3;
    private static final int COST_INDEX = 4;

    public FileWorker() {
    }

    public static void generateFile(String path, int n) throws IOException {
        try (BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < n; ++i) {
                HospitalReception hospitalReception = HospitalReceptionFactory.generateHospitalReception();

                String formattedDate = hospitalReception.date().toString();
                bufferedReader.write(String.format(
                        "%s;%s;%s;%s;%d%n",
                        formattedDate,
                        hospitalReception.doctorFullName(),
                        hospitalReception.specialization(),
                        hospitalReception.patientFullName(),
                        hospitalReception.cost()
                ));
            }
        }
    }

    public static List<HospitalReception> getFromFile(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines().map(line -> {
               String[] partsOFLine = line.split(";");
                LocalDate date = LocalDate.parse(partsOFLine[DATE_INDEX]);
                String doctorFullName = partsOFLine[DOCTOR_FULL_NAME_INDEX];
                Specialization specialization = Specialization.valueOf(partsOFLine[SPECIALIZATION_INDEX]);
                String patientFullName = partsOFLine[PATIENT_FULL_NAME_INDEX];
                int cost = Integer.parseInt(partsOFLine[COST_INDEX]);

                return new HospitalReception(date, doctorFullName, specialization, patientFullName, cost);
            }).toList();
        }
    }
}