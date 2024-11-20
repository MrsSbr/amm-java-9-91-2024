package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class TicketContainer {
    private List<Plays> plays = new ArrayList<>();

    public TicketContainer(){
    }

    public TicketContainer(List<Plays> plays){
        this.plays = plays;
    }

    public void fill(List<String> performances, Scanner scanner, int countTickets, String message) {

        int currentCount = 0;

        for (String perform : performances) {

            System.out.println(message + perform);

            int count = scanner.nextInt();
            currentCount += count;

            if (currentCount > countTickets)
                throw new RuntimeException("Вы ввели слишком много билетов");

            if (count < 0)
                throw new RuntimeException("Неверное значение");

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

    public void findPopular() {
        plays.sort((play1, play2) -> Integer.compare(play2.getCountTickets(), play1.getCountTickets()));

        int max = plays.getFirst().getCountTickets();
        plays.stream()
                .filter(play -> play.getCountTickets() == max)
                .map(Plays::getName)
                .forEach(System.out::println);

    }

    public void notNullPlays() {
        plays.stream()
                .filter(play -> play.getCountTickets() > 0)
                .map(Plays::getName)
                .forEach(System.out::println);
    }

    public int getSize() {
        return plays.size();
    }

    public List<Plays> getPlays() {
        return plays;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Plays play: plays) {
            result.append(play.getName()).append(", ").append(play.getCountTickets()).append("\n");
        }

        return result.toString();
    }
}
