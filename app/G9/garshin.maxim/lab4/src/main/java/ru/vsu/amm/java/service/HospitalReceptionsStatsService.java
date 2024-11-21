package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.enums.Specialization;

import java.time.LocalDate;
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

        Set<String> allDoctors = receptions.stream()
                .map(HospitalReception::doctorFullname)
                .collect(Collectors.toSet());

        return receptions.stream()
                .collect(Collectors.groupingBy(HospitalReception::patientFullname))
                .entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .map(HospitalReception::doctorFullname)
                        .collect(Collectors.toSet())
                        .containsAll(allDoctors))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static Map<String, Set<String>> findPatientsVisitedDoctorsLastYearAndNotThisYear(List<HospitalReception> receptions) throws NullPointerException {
        if (receptions == null) {
            throw new NullPointerException();
        }

        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate endOfYear = LocalDate.now().plusYears(1).withDayOfYear(1).minusDays(1);

        Map<String, Set<String>> patientsLastYear = receptions.stream()
                .filter(reception -> reception.date().isAfter(startOfYear.minusYears(1)) && reception.date().isBefore(endOfYear))
                .collect(Collectors.groupingBy(HospitalReception::doctorFullname,
                        Collectors.mapping(HospitalReception::patientFullname, Collectors.toSet())));

        Set<String> patientsThisYear = receptions.stream()
                .filter(reception -> reception.date().isAfter(startOfYear) && reception.date().isBefore(endOfYear))
                .map(HospitalReception::patientFullname)
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