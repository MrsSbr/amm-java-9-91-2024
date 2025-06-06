package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.GameWalkthrough;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.enums.Rating;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public final class FileWorker {
    private FileWorker(){
    }

    public static void generateFile(String path, int n) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < n; i++) {
                GameWalkthrough gameWalkthrough = GameWalkthroughFactory.generateGameWalkthrough();
                writer.write(String.format(
                        "%s;%s;%s;%d;%s%n",
                        gameWalkthrough.name(),
                        gameWalkthrough.genre(),
                        gameWalkthrough.date(),
                        gameWalkthrough.time(),
                        gameWalkthrough.rating())
                );
            }
        }
    }

    public static List<GameWalkthrough> getFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().map(line -> {
                String[] parts = line.split(";");
                String name = parts[0];
                Genre genre = Genre.valueOf(parts[1]);
                LocalDate date = LocalDate.parse(parts[2]);
                int time = Integer.parseInt(parts[3]);
                Rating rating = Rating.valueOf(parts[4]);
                return new GameWalkthrough(name, genre, date, time, rating);
            }).toList();
        }
    }
}
