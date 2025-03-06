package ru.vsu.amm.java;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hospital {
    private static final String PATH = "app/G9/tupikova.viktoria/lab4/doctor_appointments.txt";

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            DoctorAppointmentManager.saveToFile(PATH, 30);
        }
        List<DoctorAppointment> doctorAppointments = DoctorAppointmentManager.loadFromFile(PATH);

        for (Specialization specialization : Specialization.values()) {
            System.out.println(specialization.toString() + ": " + DoctorAppointmentManager.getIncomeBySpecialization(doctorAppointments, specialization));
        }
        System.out.println("\n-----------------------------------------\n");

        Set<String> patients = DoctorAppointmentManager.getPatientsForAllDoctors(doctorAppointments);
        if(!patients.isEmpty()) {
            for (String patient : patients) {
                System.out.println(patient);
            }
        } else {
            System.out.println("No patients found");
        }


        System.out.println("\n-----------------------------------------\n");
        Map<String, Set<String>> map = DoctorAppointmentManager.getPatientsLastYearNotThisYear(doctorAppointments);
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            System.out.println("Doctor: " + entry.getKey());
            System.out.println("Patients: ");
            for (String patient : entry.getValue()) {
                System.out.println(patient);
            }
            System.out.println();
        }
    }
}