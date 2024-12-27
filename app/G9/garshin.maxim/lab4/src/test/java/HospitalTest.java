import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.enums.Specialization;
import ru.vsu.amm.java.service.HospitalReceptionsStatsService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HospitalTest {

    @Test
    void testSumIncomeBySpecializationNullInput() {
        assertThrows(NullPointerException.class, () -> HospitalReceptionsStatsService.sumIncomeBySpecializationByLastYear(null));
    }

    @Test
    void testFindPatientsVisitedAllDoctorsNullInput() {
        assertThrows(NullPointerException.class, () -> HospitalReceptionsStatsService.findPatientsVisitedAllDoctors(null));
    }

    @Test
    void testFindPatientsVisitedDoctorsLastYearAndNotThisYear() {
        assertThrows(NullPointerException.class, () -> HospitalReceptionsStatsService.findPatientsVisitedDoctorsLastYearAndNotThisYear(null));
    }

    @Test
    void testSumIncomeBySpecializationHappyPath() {
        List<HospitalReception> receptions = Arrays.asList(
                new HospitalReception(LocalDate.now().minusMonths(6), "Dr. Dre", Specialization.ORTHOPEDIST, "Sharikov", 220),
                new HospitalReception(LocalDate.now().minusMonths(3), "Dr. House", Specialization.DERMATOLOGIST, "Barboskin", 1122),
                new HospitalReception(LocalDate.now().minusMonths(1), "Dr. Mom", Specialization.ORTHOPEDIST, "Pupkin", 550)
        );
        Map<Specialization, Integer> result = HospitalReceptionsStatsService.sumIncomeBySpecializationByLastYear(receptions);

        assertEquals(770, result.get(Specialization.ORTHOPEDIST));
        assertEquals(1122, result.get(Specialization.DERMATOLOGIST));
        assertFalse(result.containsKey(Specialization.PEDIATRICIAN));
    }

    @Test
    void testFindPatientsVisitedAllDoctorsHappyPath() {
        List<HospitalReception> receptions = Arrays.asList(
                new HospitalReception(LocalDate.now().minusYears(1), "Dr. Dre", Specialization.ORTHOPEDIST, "Sharikov", 220),
                new HospitalReception(LocalDate.now().minusMonths(3), "Dr. House", Specialization.DERMATOLOGIST, "Sharikov", 1122),
                new HospitalReception(LocalDate.now().minusMonths(1), "Dr. Dre", Specialization.ORTHOPEDIST, "Pupkin", 550)
        );
        Set<String> result = HospitalReceptionsStatsService.findPatientsVisitedAllDoctors(receptions);

        assertEquals(Set.of("Sharikov"), result);
    }

    @Test
    void testFindPatientsVisitedDoctorsLastYearAndNotThisYearHappyPath() {
        List<HospitalReception> receptions = Arrays.asList(
                new HospitalReception(LocalDate.now().minusYears(1), "Dr. Dre", Specialization.ORTHOPEDIST, "Sharikov", 220),
                new HospitalReception(LocalDate.now(), "Dr. House", Specialization.DERMATOLOGIST, "Barboskin", 1122),
                new HospitalReception(LocalDate.now().minusMonths(1), "Dr. Dre", Specialization.ORTHOPEDIST, "Pupkin", 550)
        );
        Map<String, Set<String>> result = HospitalReceptionsStatsService.findPatientsVisitedDoctorsLastYearAndNotThisYear(receptions);

        assertEquals(Set.of("Barboskin"), result.get("Dr. House"));
        assertTrue(result.get("Dr. Dre").isEmpty());
    }
}