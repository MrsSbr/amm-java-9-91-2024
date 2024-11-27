package ru.vsu.amm.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Enum.Illness;
import ru.vsu.amm.java.Model.Patient;
import ru.vsu.amm.java.Repository.PatientRepo;
import ru.vsu.amm.java.Service.PatientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PatientServiceTest {

    @Test
    @DisplayName("Количество здоровых (прошедших обследование в последний год)")
    void testHealthyPeopleCount() {
        List<Patient> patients = List.of(
                new Patient("123", "John", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusYears(5)),
                new Patient("124", "Jane", "Smith", "Ann", false, Illness.PNEUMONIA, LocalDate.now().minusYears(3)),
                new Patient("234", "Emily", "Williams", "Lee", false, Illness.ASTHMA, LocalDate.now().minusYears(3)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(1)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusMonths(3)),
                new Patient("237", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(3)));

        List<Patient> expected = List.of(
                new Patient("236", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(1)));

        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByIsHealthy();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Количество здоровых (прошедших обследование в последний год) с одинаковыми СНИЛС (индвид номер). Допустим, человек сменил имя")
    void testHealthyPeopleCountWithSameIndividualNumber() {
        List<Patient> patients = List.of(
                new Patient("123", "Smith", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusMonths(6)),
                new Patient("123", "John", "Doe", "Michael", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(1)));

        List<Patient> expected = new ArrayList<>();


        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByIsHealthy();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Количество здоровых (прошедших обследование в последний год) если таковых нет")
    void testHealthyPeopleCountButNoHealthy() {
        List<Patient> patients = List.of(
                new Patient("123", "Smith", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusMonths(6)),
                new Patient("125", "John", "Doe", "Michael", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(1)));

        List<Patient> expected = List.of(
                new Patient("123", "Smith", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusMonths(6)));


        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByIsHealthy();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("список людей которые проходили обследование за последние 3 года")
    void testPeopleThatVisitedInLastThreeYears() {
        List<Patient> patients = List.of(
                new Patient("123", "John", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusYears(5)),
                new Patient("124", "Jane", "Smith", "Ann", false, Illness.PNEUMONIA, LocalDate.now().minusYears(2)),
                new Patient("234", "Emily", "Williams", "Lee", false, Illness.ASTHMA, LocalDate.now().minusYears(3)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(1)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusMonths(3)),
                new Patient("237", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(3).plusMonths(1)));

        List<Patient> expected = List.of(
                new Patient("124", "Jane", "Smith", "Ann", false, Illness.PNEUMONIA, LocalDate.now().minusYears(3)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(1)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusMonths(3)),
                new Patient("237", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(3).plusMonths(1)));

        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByDateAfter();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("список людей которые проходили обследование за последние 3 года если таковых нет")
    void testPeopleThatVisitedInLastThreeYearsButNoSuchInList() {
        List<Patient> patients = List.of(
                new Patient("123", "John", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusYears(5)),
                new Patient("124", "Jane", "Smith", "Ann", false, Illness.PNEUMONIA, LocalDate.now().minusYears(5)),
                new Patient("234", "Emily", "Williams", "Lee", false, Illness.ASTHMA, LocalDate.now().minusYears(5)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(5)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusYears(5)),
                new Patient("237", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusYears(5)));

        List<Patient> expected = new ArrayList<>();

        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByDateAfter();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("список людей, которые проходили обследование последние 5 лет, но не проходили последние 2 года")
    void testPeopleThatVisitedInLastFiveButNotInLastTwoYears() {

        List<Patient> patients = List.of(
                new Patient("123", "John", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusYears(4)),
                new Patient("124", "John", "Smith", "Ann", true, Illness.HEALTHY, LocalDate.now().minusYears(1)),
                new Patient("124", "Jane", "Smith", "Ann", false, Illness.PNEUMONIA, LocalDate.now().minusYears(4)),
                new Patient("234", "Emily", "Williams", "Lee", false, Illness.ASTHMA, LocalDate.now().minusYears(4)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(4)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusMonths(5)),
                new Patient("236", "Michael", "Brown", "James", true, Illness.HEALTHY, LocalDate.now().minusMonths(1)),
                new Patient("237", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(5)),
                new Patient("238", "Olivia", "Jones", "Ray", false, Illness.BRONCHITIS, LocalDate.now().minusMonths(1)));

        List<Patient> expected = List.of(
                new Patient("123", "John", "Doe", "Michael", true, Illness.HEALTHY, LocalDate.now().minusYears(4)),
                new Patient("234", "Emily", "Williams", "Lee", false, Illness.ASTHMA, LocalDate.now().minusYears(4)),
                new Patient("235", "Robert", "Johnson", "David", true, Illness.HEALTHY, LocalDate.now().minusYears(4)));


        PatientService patientService = new PatientService(new PatientRepo(patients));
        List<Patient> actual = patientService.findByDateBetween();
        assertEquals(expected, actual);

    }

}
