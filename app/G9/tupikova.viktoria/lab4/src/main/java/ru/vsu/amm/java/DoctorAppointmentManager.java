package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DoctorAppointmentManager {
    private final static Random random = new Random();

    private static final String[] DOCTORS = {
            "John Smith", "Emily Johnson", "Michael Brown", "Sarah Davis", "David Wilson"
    };

    private static final String[] PATIENTS = {
            "James Miller", "Mary Taylor", "Robert Anderson", "Linda Thomas", "William Jackson"
    };

    public static int getIncomeBySpecialization(List<DoctorAppointment> doctorAppointments, Specialization specialization) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return doctorAppointments.stream()
                .filter(appointment -> appointment.getSpecialization().equals(specialization))
                .filter(appointment -> appointment.getDate().isAfter(oneYearAgo))
                .mapToInt(DoctorAppointment::getCost)
                .sum();
    }

    public static Set<String> getPatientsForAllDoctors(List<DoctorAppointment> doctorAppointments) {
        Map<String, Set<String>> patientsByDoctor = doctorAppointments.stream()
                .collect(Collectors.groupingBy(
                        DoctorAppointment::getDoctorFullName,
                        Collectors.mapping(DoctorAppointment::getPatientFullName, Collectors.toSet())
                ));

        return patientsByDoctor.values().stream()
                .reduce((patientsSet1, patientsSet2) -> {
                    patientsSet1.retainAll(patientsSet2);
                    return patientsSet1;
                })
                .orElse(new HashSet<>());
    }

    public static Map<String, Set<String>> getPatientsLastYearNotThisYear(List<DoctorAppointment> doctorAppointments) {
        LocalDate now = LocalDate.now();

        LocalDate lastYearStart = LocalDate.of(now.getYear() - 1, 1, 1);
        LocalDate lastYearEnd = LocalDate.of(now.getYear() - 1, 12, 31);
        LocalDate thisYearStart = LocalDate.of(now.getYear(), 1, 1);
        LocalDate thisYearEnd = LocalDate.of(now.getYear(), 12, 31);

        Map<String, Set<String>> patientsLastYear = getPatientsByYear(doctorAppointments, lastYearStart, lastYearEnd);
        Map<String, Set<String>> patientsThisYear = getPatientsByYear(doctorAppointments, thisYearStart, thisYearEnd);

        return patientsLastYear.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            Set<String> lastYearPatients = entry.getValue();
                            Set<String> thisYearPatients = patientsThisYear.getOrDefault(entry.getKey(), new HashSet<>());

                            return lastYearPatients.stream()
                                    .filter(patient -> !thisYearPatients.contains(patient))
                                    .collect(Collectors.toSet());
                        }
                ));
    }

    private static Map<String, Set<String>> getPatientsByYear(List<DoctorAppointment> doctorAppointments, LocalDate startDate, LocalDate endDate) {
        return doctorAppointments.stream()
                .filter(appointment -> !appointment.getDate().isBefore(startDate) && !appointment.getDate().isAfter(endDate))
                .collect(Collectors.groupingBy(
                        DoctorAppointment::getDoctorFullName,
                        Collectors.mapping(DoctorAppointment::getPatientFullName, Collectors.toSet())
                ));
    }

    public static DoctorAppointment generateDoctorAppointment() {
        int year = LocalDate.now().getYear() - random.nextInt(3);
        int month = random.nextInt(12) + 1;
        int dayOfMonth = random.nextInt(28) + 1;
        LocalDate date = LocalDate.of(year, month, dayOfMonth);

        String doctorName = DOCTORS[random.nextInt(DOCTORS.length)];

        Specialization specialization = Specialization.values()[random.nextInt(Specialization.values().length)];

        String patientName = PATIENTS[random.nextInt(PATIENTS.length)];

        int cost = 1000 + random.nextInt(5000);

        return new DoctorAppointment(date, doctorName, specialization, patientName, cost);
    }

    public static void saveToFile(String path, int n) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for (int i = 0; i < n; i++) {
                DoctorAppointment appointment = generateDoctorAppointment();
                writer.write(String.format("%s;%s;%s;%s;%d\n",
                        appointment.getDate(),
                        appointment.getDoctorFullName(),
                        appointment.getSpecialization(),
                        appointment.getPatientFullName(),
                        appointment.getCost()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<DoctorAppointment> loadFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDate date = LocalDate.parse(parts[0]);
                        String doctorFullName = parts[1];
                        Specialization specialization = Specialization.valueOf(parts[2]);
                        String patientFullName = parts[3];
                        int cost = Integer.parseInt(parts[4]);

                        return new DoctorAppointment(date, doctorFullName, specialization, patientFullName, cost);
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
