package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Tournaments {
    private List<Fight> fights;

    public Tournaments(List<Fight> fights) {
        this.fights = fights;
    }

    public Tournaments(String filePath) throws IOException {
        fights = readFromFile(filePath);
    }

    public Tournaments() {
    }

    private List<Fight> readFromFile(String filePath) throws IOException {
        List<Fight> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("dd.MM.yyyy");
            line = reader.readLine();

            while(line != null) {
                String[] words = line.split(", ");
                Fight fight = getFight(words, dateFormat);
                result.add(fight);

                line = reader.readLine();
            }
        }
        return result;
    }

    private static Fight getFight(String[] words, DateTimeFormatter dateFormat) throws IOException {
        int tournamentNumber = Integer.parseInt(words[0]);
        String firstFighter = words[1];
        String secondFighter = words[2];
        String winner = words[3];
        boolean fatality = Boolean.parseBoolean(words[4]);

        String fightDate = words[5];
        LocalDate date;
        try {
            date = LocalDate.parse(fightDate, dateFormat);
        } catch (DateTimeParseException e) {
            throw new IOException("Невозможно преобразовать дату: " + fightDate, e);
        }

        Fight fight = new Fight(tournamentNumber, firstFighter, secondFighter, winner, fatality, date);
        return fight;
    }

    public void add(Fight fight) {
        fights.add(fight);
    }

    public void delete(Fight fight) {
        fights.remove(fight);
    }

    public List<Fight> getFights() {
        return fights;
    }
}
