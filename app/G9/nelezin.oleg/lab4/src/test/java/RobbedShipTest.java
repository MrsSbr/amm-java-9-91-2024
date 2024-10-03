import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipClass;
import ru.vsu.amm.java.service.RobbedShipService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobbedShipTest {

    private RobbedShipService robbedShipService;

    @BeforeEach
    void setup() {
        robbedShipService = new RobbedShipService();
    }

    @Test
    public void testBoardedShipsByNationality() {
        List<RobbedShip> ships = Arrays.asList(
                new RobbedShip(LocalDate.of(2023, 1, 10), ShipClass.FRIGATE, Nationality.PORTUGAL, 100, 20, true),
                new RobbedShip(LocalDate.of(2023, 1, 15), ShipClass.BRIGANTINE, Nationality.BRITAIN, 50, 10, true),
                new RobbedShip(LocalDate.of(2023, 1, 20), ShipClass.CARAVEL, Nationality.PORTUGAL, 75, 15, false)
        );
        Map<Nationality, Long> result = robbedShipService.boardedShipsByNationality(ships);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(Nationality.PORTUGAL));
        assertEquals(1L, result.get(Nationality.BRITAIN));
    }

    @Test
    public void testLeastProfitableMonth() {
        List<RobbedShip> ships = Arrays.asList(
                new RobbedShip(LocalDate.of(2023, 1, 10), ShipClass.FRIGATE, Nationality.PORTUGAL, 100, 20, true),
                new RobbedShip(LocalDate.of(2023, 1, 15), ShipClass.BRIGANTINE, Nationality.BRITAIN, 50, 10, true),
                new RobbedShip(LocalDate.of(2023, 2, 20), ShipClass.CARAVEL, Nationality.PORTUGAL, 75, 15, false)
        );

        Optional<Map.Entry<LocalDate, Integer>> result = robbedShipService.leastProfitableMonth(ships);

        assertTrue(result.isPresent());
        assertEquals(LocalDate.of(2023, 1, 1), result.get().getKey());
        assertEquals(150, result.get().getValue());
    }

    @Test
    public void testTopRumShips() {
        List<RobbedShip> ships = Arrays.asList(
                new RobbedShip(LocalDate.now().minusYears(2), ShipClass.FRIGATE, Nationality.PORTUGAL, 100, 20, true),
                new RobbedShip(LocalDate.now().minusYears(1), ShipClass.BRIGANTINE, Nationality.BRITAIN, 50, 30, true),
                new RobbedShip(LocalDate.now().minusYears(4), ShipClass.CARAVEL, Nationality.PORTUGAL, 75, 15, false)
        );

        List<RobbedShip> result = robbedShipService.topRumShips(ships);

        assertEquals(2, result.size());
        assertEquals(30, result.get(0).getBarrelCount());
        assertEquals(20, result.get(1).getBarrelCount());
    }
}
