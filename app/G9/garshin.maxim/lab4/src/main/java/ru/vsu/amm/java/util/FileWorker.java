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
                        hospitalReception.doctorFullname(),
                        hospitalReception.specialization(),
                        hospitalReception.patientFullname(),
                        hospitalReception.cost()
                ));
            }
        }
    }

    public static List<HospitalReception> getFromFile(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines().map(line -> {
               String[] parts = line.split(";");
                LocalDate date = LocalDate.parse(parts[0]);
                String doctorFullname = parts[1];
                Specialization specialization = Specialization.valueOf(parts[2]);
                String patientFullname = parts[3];
                int cost = Integer.parseInt(parts[4]);

                return new HospitalReception(date, doctorFullname, specialization, patientFullname, cost);
            }).toList();
        }
    }
}