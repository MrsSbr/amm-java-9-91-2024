package ru.vsu.amm.java;

import java.util.List;
import java.util.Scanner;

import ru.vsu.amm.java.classes.Menu;
import ru.vsu.amm.java.classes.PerformanceContainer;
import ru.vsu.amm.java.classes.PerformanceManager;
import ru.vsu.amm.java.classes.PerformanceUtils;

public class TheaterTickets {
    private static final int COUNT_OF_TICKETS = 24000;


    public static void main(String[] args) {

        List<String> performances = List.of(
                "Лебединое озеро",
                "Евгений Онегин",
                "Пиковая дама",
                "Спящая красавица",
                "Щелкунчик",
                "Алеко",
                "Мастер и Маргарита",
                "Анна Каренина"
        );

        int choice;
        do {
            Scanner scanner = new Scanner(System.in);
            PerformanceManager performanceManager = new PerformanceManager();
            Menu menu = new Menu();

            menu.printFillMenu();
            choice = scanner.nextInt();

            switch (choice) {

                case 1: {
                    try {
                        performanceManager.fill(performances, scanner, COUNT_OF_TICKETS,
                                "Введите количество билетов на спектакль: ");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 2: {
                    performanceManager.fillRandomly(performances, COUNT_OF_TICKETS);
                    break;
                }
            }

            if (choice == 1 || choice == 2) {
                PerformanceContainer performanceContainer = new PerformanceContainer(performanceManager.getPlays());
                System.out.println("Полученный контейнер" + performanceContainer);

                PerformanceUtils performanceUtils = new PerformanceUtils(performanceContainer.getPlays());
                System.out.println("Самые популярные спектакли: ");
                System.out.println(performanceUtils.findPopular());

                System.out.println("Спектакли на которые приобрели билеты:");
                System.out.println(performanceUtils.notNullPlays());
            }

        } while (choice == 1 || choice == 2);
    }
}