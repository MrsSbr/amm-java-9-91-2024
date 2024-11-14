import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PowerPlant;
import ru.vsu.amm.java.enums.Type;
import ru.vsu.amm.java.service.PowerPlantService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerPlantTest {

    @Test
    void testGetTypeWithPowerOver50LastMonthHappyPath() {
        List<PowerPlant> powerPlants = List.of(
                new PowerPlant(LocalDate.now().minusDays(10), Type.SOLAR, 60),
                new PowerPlant(LocalDate.now().minusDays(5), Type.WIND, 70),
                new PowerPlant(LocalDate.now().minusDays(40), Type.NUCLEAR, 30)
        );

        Set<Type> result = PowerPlantService.getTypesWithPowerOver50LastMonth(powerPlants);
        assertEquals(Set.of(Type.SOLAR, Type.WIND), result);
    }

    @Test
    void testGetTypesWithPowerOver50LastMonthNull() {
        Set<Type> result = PowerPlantService.getTypesWithPowerOver50LastMonth(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTypesWithPowerOver50LastMonthEmpty() {
        Set<Type> result = PowerPlantService.getTypesWithPowerOver50LastMonth(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAveragePowerByTypeLastThreeMonthsHappyPath() {
        List<PowerPlant> powerPlants = List.of(
                new PowerPlant(LocalDate.now().minusDays(20), Type.SOLAR, 60),
                new PowerPlant(LocalDate.now().minusDays(10), Type.SOLAR, 80),
                new PowerPlant(LocalDate.now().minusDays(5), Type.WIND, 70)
        );

        Map<Type, Double> result = PowerPlantService.getAveragePowerByTypeLastThreeMonths(powerPlants);
        assertEquals(70.0, result.get(Type.SOLAR));
        assertEquals(70.0, result.get(Type.WIND));
        assertEquals(2, result.size());
    }

    @Test
    void testGetAveragePowerByTypeLastThreeMonthsNull() {
        Map<Type, Double> result = PowerPlantService.getAveragePowerByTypeLastThreeMonths(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAveragePowerByTypeLastThreeMonthsEmpty() {
        Map<Type, Double> result = PowerPlantService.getAveragePowerByTypeLastThreeMonths(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTotalProductionLastYearHappyPath() {
        List<PowerPlant> powerPlants = List.of(
                new PowerPlant(LocalDate.now().minusMonths(6), Type.SOLAR, 100),
                new PowerPlant(LocalDate.now().minusMonths(8), Type.SOLAR, 200),
                new PowerPlant(LocalDate.now().minusMonths(2), Type.WIND, 300)
        );

        int totalProduction = PowerPlantService.getTotalProductionLastYear(powerPlants);
        assertEquals(300, totalProduction);
    }

    @Test
    void testGetTotalProductionLastYearNull() {
        int totalProduction = PowerPlantService.getTotalProductionLastYear(null);
        assertEquals(0, totalProduction);
    }

    @Test
    void testGetTotalProductionLastYearEmpty() {
        int totalProduction = PowerPlantService.getTotalProductionLastYear(List.of());
        assertEquals(0, totalProduction);
    }
}