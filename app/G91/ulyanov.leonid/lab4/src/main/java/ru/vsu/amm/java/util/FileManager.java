package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.Ingredient;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    private static String getIngredientsAsString(List<Ingredient> ingredients) {
        return String.join(" ",
                ingredients.stream()
                        .map(Object::toString)
                        .toList()) + "\n";
    }

    private static List<Ingredient> getIngredientsFromString(String line) {
        return Arrays.stream(line.split("\\s+"))
                .map(Ingredient::valueOf)
                .toList();
    }

    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    public static void writeToFile(String dest, int size) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dest, true))) {
            MoonshineDataGenerator gen = new MoonshineDataGenerator();
            for (int i = 0; i < size; i++) {
                MoonshineData drink = gen.generateOneMoonshineData();
                bw.write(String.format("%s;%s%n",
                        drink.label(),
                        drink.date()));
                bw.write(getIngredientsAsString(drink.ingredients()));
                bw.write(String.format(Locale.US, "%.5f;%.5f%n",
                        drink.volume(),
                        drink.makingTime()));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static List<MoonshineData> readFromFile(String source) {
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String[] lines = br.lines().toArray(String[]::new);
            List<MoonshineData> list = new ArrayList<>();
            for (int i = 0; i < lines.length - 2; i += 3) {
                String[] data = lines[i].split(";");
                String label = data[0];
                LocalDate date = LocalDate.parse(data[1]);
                List<Ingredient> ingredients = getIngredientsFromString(lines[i + 1]);
                data = lines[i + 2].split(";");
                float volume = Float.parseFloat(data[0]);
                float makingTime = Float.parseFloat(data[1]);
                list.add(new MoonshineData(date, label, ingredients, volume, makingTime));
            }
            return list;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
