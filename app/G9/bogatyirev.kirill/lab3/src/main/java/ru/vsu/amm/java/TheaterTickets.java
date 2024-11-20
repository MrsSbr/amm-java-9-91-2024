package ru.vsu.amm.java;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ru.vsu.amm.java.classes.Menu;
import ru.vsu.amm.java.classes.TicketContainer;

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
            TicketContainer ticketContainer = new TicketContainer();
            Menu menu = new Menu();

            menu.printFillMenu();
            choice = scanner.nextInt();

            switch (choice) {

                case 1: {
                    try {
                        ticketContainer.fill(performances, scanner, COUNT_OF_TICKETS,
                                "Введите количество билетов на спектакль: ");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 2: {
                    ticketContainer.fillRandomly(performances, COUNT_OF_TICKETS);
                    break;
                }
            }

            if (choice == 1 || choice == 2) {
                System.out.println("Полученный контейнер" + ticketContainer);

                System.out.println("Самые популярные спектакли: ");
                ticketContainer.findPopular();

                System.out.println("Спектакли на которые приобрели билеты:");
                ticketContainer.notNullPlays();
            }

        } while (choice == 1 || choice == 2);
    }
}