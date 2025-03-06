package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Winner;
import ru.vsu.amm.java.enums.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class DataGenerator {

    private static final Logger logger = Logger.getLogger(DataGenerator.class.getName());

    public static List<Winner> generateWinners(int count) {
        logger.info("call generateWinners");
        List<String> names = List.of("Alice", "Bob", "Lisa", "Tom", "Evelin");
        List<Department> departments = List.of(Department.SALES, Department.MARKETING,
                Department.ENGINEERING, Department.HR,
                Department.FINANCE);

        Random random = new Random();
        List<Winner> winners = new ArrayList<>();

        for (int year = 2000; year <= 2000 + count; year++) {
            String name = names.get(random.nextInt(names.size()));
            Department department = departments.get(random.nextInt(departments.size()));
            winners.add(new Winner(year, name, department));
        }
        return winners;
    }
}