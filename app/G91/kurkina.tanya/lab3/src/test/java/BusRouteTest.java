import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.BusRevenueManager;
import ru.vsu.amm.java.classes.BusRoute;

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
    public void addDailyRevenueTest() {
        BusRoute route = new BusRoute(1);
        assertTrue(route.getDailyRevenues().isEmpty());//что список ежедневной выручки пустой

        route.addDailyRevenue(1000);
        route.addDailyRevenue(2000);

        assertEquals(2, route.getDailyRevenues().size());//на корректность размера списка и итоговой суммы
        assertEquals(3000, route.getTotalWeeklyRevenue());
    }

    @Test
    public void collectWeeklyDataTest() {
        manager.collectWeeklyData();

        List<BusRoute> routes = manager.getRoutes();
        assertEquals(10, routes.size());

        for (BusRoute route : routes) {
            assertEquals(7, route.getDailyRevenues().size()); //маршрут должен иметь данные за 7 дней
            int totalRevenue = route.getTotalWeeklyRevenue();
            assertTrue(totalRevenue >= 0);//общая выручка не должна быть отрицательной
            assertTrue(totalRevenue <= 35000);//и не больше максимума
        }
    }

    @Test
    public void printWeeklyReportTest() {
        manager.collectWeeklyData();
        manager.printWeeklyReport();

        List<BusRoute> routes = manager.getRoutes();
        assertEquals(10, routes.size());//что отчет корректно ссылается на 10 маршрутов
    }

    @Test
    public void emptyRoutesTest() {
        List<BusRoute> routes = manager.getRoutes();
        //проверяем, что маршруты созданы но выручка нигде не заполнена
        for (BusRoute route : routes) {
            assertTrue(route.getDailyRevenues().isEmpty());
            assertEquals(0, route.getTotalWeeklyRevenue());
        }
    }
}
