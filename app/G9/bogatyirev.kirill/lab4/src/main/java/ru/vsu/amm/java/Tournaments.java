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
    public static final int TOURNAMENT_NUMBER_INDEX = 0;
    public static final int FIRST_FIGHTER_NAME_INDEX = 1;
    public static final int SECOND_FIGHTER_NAME_INDEX = 2;
    public static final int WINNER_NAME_INDEX = 3;
    public static final int FATALITY_INDEX = 4;
    public static final int DATE_INDEX = 5;



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
        int tournamentNumber = Integer.parseInt(words[TOURNAMENT_NUMBER_INDEX]);
        String firstFighter = words[FIRST_FIGHTER_NAME_INDEX];
        String secondFighter = words[SECOND_FIGHTER_NAME_INDEX];
        String winner = words[WINNER_NAME_INDEX];
        boolean fatality = Boolean.parseBoolean(words[FATALITY_INDEX]);

        String fightDate = words[DATE_INDEX];
        LocalDate date;
        try {
            date = LocalDate.parse(fightDate, dateFormat);
        } catch (DateTimeParseException e) {
            throw new IOException("Невозможно преобразовать дату: " + fightDate, e);
        }

        return new Fight(tournamentNumber, firstFighter, secondFighter, winner, fatality, date);
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
