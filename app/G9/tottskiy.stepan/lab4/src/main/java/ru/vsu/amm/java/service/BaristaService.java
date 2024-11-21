package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.DrinkRecord;
import ru.vsu.amm.java.enums.DrinkName;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BaristaService {

    private static final Logger logger = Logger.getLogger(BaristaService.class.getName());

    // Список напитков, которые заказывают по утрам с 7 до 9
    public List<DrinkName> getMorningDrinks(List<DrinkRecord> drinkRecords) {
        logger.info("Начинаем анализ напитков, заказанных утром с 7 до 9.");
        return drinkRecords.stream()
                .filter(record -> {
                    int hour = record.getTimestamp().getHour();
                    return hour >= 7 && hour < 9;
                })
                .map(DrinkRecord::getDrinkName)
                .distinct()
                .collect(Collectors.toList());
    }

    // Напитки, которые не заказывали за последние 3 месяца
    public List<DrinkName> getDrinksNotOrderedLast3Months(List<DrinkRecord> drinkRecords) {
        logger.info("Анализ напитков, которые не заказывались за последние 3 месяца.");
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);

        Set<DrinkName> recentDrinks = drinkRecords.stream()
                .filter(record -> record.getTimestamp().isAfter(threeMonthsAgo))
                .map(DrinkRecord::getDrinkName)
                .collect(Collectors.toSet());

        return List.of(DrinkName.values()).stream()
                .filter(drink -> !recentDrinks.contains(drink))
                .collect(Collectors.toList());
    }

    // Сколько раз бариста готовил капучино
    public long countCappuccino(List<DrinkRecord> drinkRecords) {
        logger.info("Считаем количество приготовленных капучино.");
        return drinkRecords.stream()
                .filter(record -> DrinkName.CAPPUCHINO.equals(record.getDrinkName()))
                .count();
    }
}

