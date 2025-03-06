import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.BusRouteManager;
import ru.vsu.amm.java.BusRouteReport;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BusRouteManagerTest {

    @Test
    public void emptyRoutesTest() {
        BusRouteManager manager = new BusRouteManager();

        assertTrue(manager.getReports().isEmpty());
        assertEquals(0, manager.getWeeklyProfit());
        assertEquals(0, manager.getWeeklyProfitByRoute(1));
    }

    @Test
    void testGetWeeklyProfitByRoute_InvalidRoute() {
        BusRouteManager manager = new BusRouteManager();
        assertThrows(IllegalArgumentException.class, () -> manager.getWeeklyProfitByRoute(0));
        assertThrows(IllegalArgumentException.class, () -> manager.getWeeklyProfitByRoute(11));
    }

    @Test
    void testGenerateReports() {
        BusRouteManager manager = new BusRouteManager();
        manager.generateReports();
        assertEquals(70, manager.getReports().size());
    }

    @Test
    void testWeeklyProfitByRoute() {
        BusRouteManager manager = new BusRouteManager();

        manager.getReports().add(new BusRouteReport(1, 1000));
        manager.getReports().add(new BusRouteReport(2, 2000));
        manager.getReports().add(new BusRouteReport(1, 1500));
        manager.getReports().add(new BusRouteReport(2, 2500));
        manager.getReports().add(new BusRouteReport(3, 3000));

        int profitRoute1 = manager.getWeeklyProfitByRoute(1);
        assertEquals(2500, profitRoute1);

        int profitRoute2 = manager.getWeeklyProfitByRoute(2);
        assertEquals(4500, profitRoute2);

        int profitRoute3 = manager.getWeeklyProfitByRoute(3);
        assertEquals(3000, profitRoute3);

        int totalProfit = manager.getWeeklyProfit();
        assertEquals(10000, totalProfit);
    }
}
