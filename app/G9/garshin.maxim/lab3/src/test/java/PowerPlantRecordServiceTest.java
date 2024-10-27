import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.PowerPlantRecord;
import ru.vsu.amm.java.enums.Type;
import ru.vsu.amm.java.service.PowerPlantRecordService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PowerPlantRecordServiceTest {
    private PowerPlantRecordService powerPlantRecordService;
    private List<PowerPlantRecord> records;

    @BeforeEach
    public void init() {
        powerPlantRecordService = new PowerPlantRecordService();
        records = createRecords();
    }

    @Test
    public void testGetTypesWithProductionOver50MWLastMonth() {
        Set<Type> types = powerPlantRecordService.getTypesWithProductionOver50MWLastMonth(records);

        assertNotNull(types);
        assertTrue(types.contains(Type.BIOMASS));
        assertFalse(types.contains(Type.HYDROELECTRIC));
    }

    @Test
    public void testGetAveragePowerLastThreeMonths() {
        Map<Type, Double> averagePowerLastThreeMonths = powerPlantRecordService.getAveragePowerLastThreeMonths(records);

        assertNotNull(averagePowerLastThreeMonths);
        assertEquals(75.0, averagePowerLastThreeMonths.get(Type.WIND));
        assertFalse(averagePowerLastThreeMonths.containsKey(Type.THERMAL));
    }

    @Test
    public void testGetTotalPowerLastYear() {
        int power = powerPlantRecordService.getTotalPowerLastYear(records);

        assertNotNull(power);
        assertEquals(300, power);
        assertTrue(power < 75);
    }


    private static List<PowerPlantRecord> createRecords() {
        return Arrays.asList(
                new PowerPlantRecord(LocalDate.now().minusMonths(1), Type.BIOMASS, 100),
                new PowerPlantRecord(LocalDate.now().minusMonths(2), Type.HYDROELECTRIC, 30),
                new PowerPlantRecord(LocalDate.now().minusMonths(1), Type.NUCLEAR, 50),
                new PowerPlantRecord(LocalDate.now().minusYears(1), Type.SOLAR, 120),
                new PowerPlantRecord(LocalDate.now().minusMonths(1), Type.WIND, 70),
                new PowerPlantRecord(LocalDate.now().minusDays(16), Type.THERMAL, 10)
        );
    }


}
