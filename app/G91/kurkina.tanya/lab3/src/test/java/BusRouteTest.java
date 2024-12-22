import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.BusRevenueManager;
import ru.vsu.amm.java.BusRoute;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusRouteTest {
    private BusRevenueManager manager;

    @BeforeEach
    public void setUp() {
        manager = new BusRevenueManager();
    }

    @Test
    public void addDailyRevenueTest(){
        BusRoute route = new BusRoute(1);//что список ежедневной выручки пустой
        assertTrue(route.getDailyRevenues().isEmpty());
        route.addDailyRevenue(1000);
        route.addDailyRevenue(2000);

        assertEquals(2, route.getDailyRevenues().size());
        assertEquals(3000, route.getWeeklyRevenue());
    }

    @Test
    public void collectWeeklyDataTest(){
        manager.collectWeeklyData();
        List<BusRoute> routes = manager.getRoutes();
        assertEquals(10, routes.size());

        for (BusRoute route : routes) {
            assertEquals(7, route.getDailyRevenues().size());
            int totalRevenue = route.getWeeklyRevenue();
            assertTrue(totalRevenue >= 0);
            assertTrue(totalRevenue <= 35000);
        }
    }

    @Test
    public void printWeeklyReportTest() {
        manager.collectWeeklyData();
        manager.printWeeklyReport();
        List<BusRoute> routes = manager.getRoutes();
        assertEquals(10, routes.size());
    }

    @Test
    public void emptyRoutesTest() {
        List<BusRoute> routes = manager.getRoutes();
        for (BusRoute route : routes) {
            assertTrue(route.getDailyRevenues().isEmpty());
            assertEquals(0, route.getWeeklyRevenue());
        }
    }
}