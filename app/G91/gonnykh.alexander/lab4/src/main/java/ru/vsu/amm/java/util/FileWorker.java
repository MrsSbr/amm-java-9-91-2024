package ru.vsu.amm.java.util;

import ru.vsu.amm.java.enums.CoffeeType;
import ru.vsu.amm.java.records.CoffeeRecord;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWorker {
    public static final String PATTERN = "%s;%s;%d;%.2f";
    public static final String ERROR_WRITE_FILE = "Произошла ошибка при записи в файл:";
    public static final String ERROR_READ_FILE = "Ошибка при обработке строки: ";
    public static final String ERROR_IO = "Произошла ошибка потока:";
    private static final Logger LOGGER = Logger.getLogger(FileWorker.class.getName());

    public static List<CoffeeRecord> readInFile(String filePath) throws IOException {
        List<CoffeeRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = Arrays.stream(line.split(";"))
                        .map(String::trim)
                        .toArray(String[]::new);

                try {
                    CoffeeType name = CoffeeType.valueOf(parts[0].toUpperCase());
                    LocalDateTime date = LocalDateTime.parse(parts[1]);
                    long preparationTime = Long.parseLong(parts[2]);
                    float price = Float.parseFloat(parts[3]);

                    records.add(new CoffeeRecord(name, date, preparationTime, price));

                } catch (IllegalArgumentException | DateTimeParseException e) {
                    LOGGER.log(Level.SEVERE, ERROR_READ_FILE + line, e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, ERROR_IO, e);
            throw e;
        }

        return records;
    }

    public static void SaveToFile(List<CoffeeRecord> records, String filePath) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (CoffeeRecord record : records) {
                try {

                    String line = String.format(Locale.US, PATTERN,
                            record.name(),
                            record.date().toString(),
                            record.preparationTime(),
                            record.cost());

                    bw.write(line);
                    bw.newLine();

                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, ERROR_WRITE_FILE, e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, ERROR_IO, e);
            throw e;
        }

    }
}
