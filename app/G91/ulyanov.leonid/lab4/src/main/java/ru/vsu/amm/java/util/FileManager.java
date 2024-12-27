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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileManager {

    private static final int LABEL_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int VOLUME_INDEX = 2;
    private static final int TIME_INDEX = 3;

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    private static final String WRITE_FAIL_MSG = "Writing to file was unsuccessful.\n";
    private static final String READ_FAIL_MSG = "Reading from file was unsuccessful.\n";
    private static final String LOG_ERROR_MSG = "Error in logger creation for file manager.\n";
    private static final String LOG_PATH = System.getProperty("user.dir") + "\\file-manager.log";

    static {
        try {
            FileHandler fileHandler = new FileHandler(LOG_PATH);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, LOG_ERROR_MSG, e);
        }
    }

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
            logger.log(Level.SEVERE, WRITE_FAIL_MSG, e);
        }
    }

    public static List<MoonshineData> readFromFile(String source) {
        List<MoonshineData> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String[] lines = br.lines().toArray(String[]::new);
            for (int i = 0; i < lines.length - 1; i += 2) {
                String[] data = lines[i].split(";");

                DrinkLabel label = DrinkLabel.valueOf(data[LABEL_INDEX]);
                LocalDate date = LocalDate.parse(data[DATE_INDEX]);
                double volume = Double.parseDouble(data[VOLUME_INDEX]);
                double makingTime = Double.parseDouble(data[TIME_INDEX]);
                List<Ingredient> ingredients = getIngredientsFromString(lines[i + 1]);

                list.add(new MoonshineData(date, label, ingredients, volume, makingTime));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, READ_FAIL_MSG, e);
        }
        return list;
    }
}
