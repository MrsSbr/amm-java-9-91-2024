package ru.vsu.amm.java.util;


import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;

import java.util.ArrayList;
import java.util.List;


public class DriverScheduleGenerator {

    private static final Driver[] drivers = {
            new Driver("Адеев Владимир Станиславович", 40),
            new Driver("Сергеев Сергей Сергеевич", 42),
            new Driver("Кириллов Кирилл Кириллович", 36),
            new Driver("Щербаков Виталий Геннадиевич", 33),
            new Driver("Кириллов Кирилл Кириллович", 41),
            new Driver("Михайлов Михаил Михайлович", 34),
            new Driver("Пономарев Максим Сергеевич", 46),
            new Driver("Сергеев Александр Петрович", 27),
            new Driver("Петров Петр Петрович", 51),
            new Driver("Увалов Дмитрий Германович", 55),
            new Driver("Добров Кирилл Сергеевич", 24),
            new Driver("Хитров Евгений Борисович", 35),
            new Driver("Борисов Борис Борисович", 20)
    };

    private static final Route[] routes = {
            new Route(1, 30.5),
            new Route(2, 33),
            new Route(3, 55),
            new Route(4, 30.5),
            new Route(5, 55),
            new Route(6, 55),
            new Route(7, 30.5),
            new Route(8, 55),
            new Route(9, 55),
            new Route(10, 30.5)
    };

    private static Driver generateDriver(int generationIndex) {
        return drivers[generationIndex % drivers.length];
    }

    private static Route generateRoute(int generationIndex) {
        return routes[generationIndex % routes.length];
    }

    private static List<Route> generateRouteList(int generationIndex) {
        List<Route> routes = new ArrayList<>();
        for(int i = 0; i < 7; ++i) {
            routes.add(generateRoute(i + generationIndex));
        }
        return routes;
    }

    private static DriverSchedule generateSchedule(int generationIndex) {
        return new DriverSchedule(generateDriver(generationIndex), generateRouteList(generationIndex));
    }

    public static List<DriverSchedule> generateScheduleList(int listSize) {
        List<DriverSchedule> driverSchedules = new ArrayList<>();
        for(int i = 0; i < listSize; ++i) {
            driverSchedules.add(generateSchedule(i));
        }
        return driverSchedules;
    }
}
