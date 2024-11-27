package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.MoonshineData;
import ru.vsu.amm.java.enums.DrinkLabel;
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

    private static final int LABEL_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int VOLUME_INDEX = 2;
    private static final int TIME_INDEX = 3;

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

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

    public static void writeToFile(String dest, int size) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dest, true))) {
            MoonshineDataGenerator gen = new MoonshineDataGenerator();
            for (int i = 0; i < size; i++) {
                MoonshineData drink = gen.generateOneMoonshineData();
                bw.write(String.format(Locale.US, "%s;%s;%.5f;%.5f%n",
                        drink.label(),
                        drink.date(),
                        drink.volume(),
                        drink.makingTime()));
                bw.write(getIngredientsAsString(drink.ingredients()));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static List<MoonshineData> readFromFile(String source) {
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String[] lines = br.lines().toArray(String[]::new);
            List<MoonshineData> list = new ArrayList<>();
            for (int i = 0; i < lines.length - 1; i += 2) {
                String[] data = lines[i].split(";");

                DrinkLabel label = DrinkLabel.valueOf(data[LABEL_INDEX]);
                LocalDate date = LocalDate.parse(data[DATE_INDEX]);
                double volume = Double.parseDouble(data[VOLUME_INDEX]);
                double makingTime = Double.parseDouble(data[TIME_INDEX]);
                List<Ingredient> ingredients = getIngredientsFromString(lines[i + 1]);

                list.add(new MoonshineData(date, label, ingredients, volume, makingTime));
            }
            return list;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
