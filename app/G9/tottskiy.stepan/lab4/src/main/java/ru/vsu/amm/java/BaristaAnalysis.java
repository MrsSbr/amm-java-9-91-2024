package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.DrinkRecord;
import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.service.BaristaService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class BaristaAnalysis {

    private static final Logger logger = Logger.getLogger(BaristaAnalysis.class.getName());

    public static void main(String[] args) {

        Random random = new Random();

        List<DrinkRecord> records = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            DrinkName drinkName = DrinkName.values()[random.nextInt(DrinkName.values().length)];
            LocalDateTime timestamp = LocalDateTime.now()
                    .minus(random.nextInt(365), ChronoUnit.DAYS) // Случайная дата за последний год
                    .minus(random.nextInt(24), ChronoUnit.HOURS) // Случайное время суток
                    .minus(random.nextInt(60), ChronoUnit.MINUTES);
            records.add(new DrinkRecord(drinkName, timestamp));
        }


        BaristaService baristaService = new BaristaService();


        logger.info("Генерация данных завершена. Начинаем анализ.");

        System.out.println("Напитки, заказываемые по утрам (7–9): " + baristaService.getMorningDrinks(records));
        System.out.println("Напитки, не заказанные за последние 3 месяца: " + baristaService.getDrinksNotOrderedLast3Months(records));
        System.out.println("Количество приготовленных капучино: " + baristaService.countCappuccino(records));
    }
}
