package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PerformanceManager {
    private List<Plays> plays = new ArrayList<>();

    public void fill(List<String> performances, Scanner scanner, int countTickets, String message) throws Exception {

        int currentCount = 0;

        for (String perform : performances) {

            System.out.println(message + perform);

            int count = scanner.nextInt();
            currentCount += count;

            if (currentCount > countTickets)
                throw new Exception("Вы ввели слишком много билетов");

            if (count < 0)
                throw new Exception("Неверное значение");

            plays.add(new Plays(perform, count));
        }
    }

    public void fillRandomly(List<String> performances, int countTickets) {
        Random random = new Random();

        int currentCount = countTickets;

        for (int i = 0; i < performances.size() - 1; ++i) {

            int count = random.nextInt(currentCount);
            currentCount -= count;


            plays.add(new Plays(performances.get(i), count));
        }

        plays.add(new Plays(performances.getLast(), (countTickets + currentCount) % countTickets));
    }

    public List<Plays> getPlays() {
        return plays;
    }
}
