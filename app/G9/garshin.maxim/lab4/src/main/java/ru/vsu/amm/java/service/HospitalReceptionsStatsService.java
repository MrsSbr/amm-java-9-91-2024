package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.enums.Specialization;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HospitalReceptionsStatsService {
    public HospitalReceptionsStatsService() {
    }

    public static Map<Specialization, Integer> sumIncomeBySpecializationByLastYear(List<HospitalReception> receptions) throws NullPointerException {
        if (receptions == null) {
            throw new NullPointerException();
        }


        if(receptions.isEmpty()) {
            return Map.of();
        }

        LocalDate lastYear = LocalDate.now().minusYears(1);

        return receptions.stream()
                .filter(hospitalReception -> hospitalReception.date().isAfter(lastYear))
                .collect(Collectors.groupingBy(HospitalReception::specialization,
                         Collectors.summingInt(HospitalReception::cost)));
    }


    public static Set<String> findPatientsVisitedAllDoctors(List<HospitalReception> receptions) throws NullPointerException {
        if (receptions == null) {
            throw new NullPointerException();
        }

        if (receptions.isEmpty()) {
            return Set.of();
        }

        Set<String> allDoctors = receptions.stream()
                .map(HospitalReception::doctorFullName)
                .collect(Collectors.toSet());

        Map<String, Set<String>> patientToDoctorsMap = new HashMap<>();

        for (HospitalReception reception : receptions) {
            patientToDoctorsMap
                    .computeIfAbsent(reception.patientFullName(), k -> new HashSet<>())
                    .add(reception.doctorFullName());
        }

        return patientToDoctorsMap.entrySet().stream()
                .filter(entry -> entry.getValue().containsAll(allDoctors))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static Map<String, Set<String>> findPatientsVisitedDoctorsLastYearAndNotThisYear(List<HospitalReception> receptions) {
        if (receptions == null) {
            throw new NullPointerException();
        }

        if(receptions.isEmpty()) {
            return Map.of();
        }

        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate startOfNextYear = LocalDate.now().plusYears(1).withDayOfYear(1);

        int minusYears = 1;

        Map<String, Set<String>> patientsLastYear = receptions.stream()
                .filter(reception -> reception.date().isAfter(startOfYear.minusYears(minusYears))
                        && reception.date().isBefore(startOfNextYear))
                .collect(Collectors.groupingBy(HospitalReception::doctorFullName,
                        Collectors.mapping(HospitalReception::patientFullName, Collectors.toSet())));

        Set<String> patientsThisYear = receptions.stream()
                .filter(reception -> reception.date().isAfter(startOfYear) && reception.date().isBefore(startOfNextYear))
                .map(HospitalReception::patientFullName)
                .collect(Collectors.toSet());

        return patientsLastYear.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            Set<String> lastYearPatients = entry.getValue();
                            lastYearPatients.removeAll(patientsThisYear);
                            return lastYearPatients;
                        }
                ));
    }
}