package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import ru.vsu.amm.java.Model.PatientDTO;
import ru.vsu.amm.java.Repository.PatientRepo;
import ru.vsu.amm.java.Service.PatientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vsu.amm.java.Config.PatientConfig.*;


public class PatientServiceTest {

    private final List<PatientDTO> patients = List.of(
            new PatientDTO("John", "Doe", "Michael", true, "Healthy", NOW.minusYears(5)),
            new PatientDTO("Jane", "Smith", "Ann", false, "Pneumonia", NOW.minusYears(3)),
            new PatientDTO("Emily", "Williams", "Lee", false, "Asthma", NOW.minusYears(3)),
            new PatientDTO("Robert", "Johnson", "David", true, "Healthy", NOW.minusYears(1)),
            new PatientDTO("Michael", "Brown", "James", true, "Healthy", NOW.minusMonths(3)),
            new PatientDTO("Olivia", "Jones", "Ray", false, "Bronchitis", NOW.minusMonths(3)),
            new PatientDTO("William", "Miller", "Grace", true, "Healthy", NOW.minusMonths(3)),
            new PatientDTO("Sophia", "Davis", "Lynn", false, "Emphysema", NOW.minusMonths(3)),
            new PatientDTO("James", "Garcia", "Marie", true, "Healthy", NOW.minusMonths(3)),
            new PatientDTO("Emma", "Rodriguez", "Jane", false, "Emphysema", NOW.minusMonths(3)),
            new PatientDTO("Emma", "Brown", "Jane", false, "Emphysema", NOW.minusMonths(3))
    );

    private final PatientService patientService = new PatientService(new PatientRepo(patients));


    @Test
    @DisplayName("Количество здоровых (прошедших обследование в последний год)")
    void testHealthyPeopleCount() {
        int actual = patientService.findByIsHealthy().size();
        int expected = 3;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("список людей которые проходили обследование за последние 3 года")
    void testPeopleThatVisitedInLastThreeYears() {
        List<PatientDTO> actual = patientService.findByDateAfter();
        List<PatientDTO> expected = patients.subList(3, patients.size());
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("список людей, которые проходили обследование последние 5 лет, но не проходили последние 2 года")
    void testPeopleThatVisitedInLastFiveButNotInLastTwoYears() {
        List<PatientDTO> actual = patientService.findByDateBetween();
        List<PatientDTO> expected = patients.subList(1, 3);
        assertEquals(expected, actual);

    }

}
