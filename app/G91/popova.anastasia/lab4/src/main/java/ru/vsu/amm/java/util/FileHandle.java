package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.SaleRecord;
import ru.vsu.amm.java.enums.PreciousMetal;
import ru.vsu.amm.java.enums.Jewelry;
import ru.vsu.amm.java.enums.Gemstone;
import static ru.vsu.amm.java.util.SaleFactory.generateSaleRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


public class FileHandle {

    private static final Logger logger = Logger.getLogger(FileHandle.class.getName());

    private static final Integer DATE_INDEX = 0;
    private static final Integer METAL_INDEX = 1;
    private static final Integer JEWELRY_INDEX = 2;
    private static final Integer GEMSTONES_INDEX = 3;
    private static final Integer PRICE_INDEX = 4;

    static {
        try {
            FileHandler fh = new FileHandler("app/G91/popova.anastasia/lab4/salesrecord.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch(IOException e) {
            logger.log(Level.SEVERE, "couldn't initialize file handler for logger", e);
        }
    }

    public static void saveToFile(String path, int n) {
        logger.log(Level.INFO, "saving sale records to " + path);
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for (int i = 0; i < n; i++) {
                SaleRecord record = generateSaleRecord();
                   writer.write(String.format("%s;%s;%s;%s;%s\n",
                            record.getDate(),
                            record.getPreciousMetal(),
                            record.getJewelry(),
                            record.getGemstones().stream()
                                   .map(Enum::name)
                                   .collect(Collectors.joining(",")),
                            record.getPrice()));

            }
            logger.log(Level.INFO, "file saved successfully");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "couldn't save the file", e);
            System.out.println("oops! couldn't save the file\n");
        }
    }

    public static List<SaleRecord> getFromFile(String path) {
        logger.log(Level.INFO, "getting sale records from " + path);
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<SaleRecord> records = reader.lines().map(line -> {
                String[] recordFragments = line.split(";");
                LocalDate date = LocalDate.parse(recordFragments[DATE_INDEX]);
                PreciousMetal preciousMetal = PreciousMetal.valueOf(recordFragments[METAL_INDEX]);
                Jewelry jewelry = Jewelry.valueOf(recordFragments[JEWELRY_INDEX]);
                Set<Gemstone> gemstones = Arrays.stream(recordFragments[GEMSTONES_INDEX].split(","))
                        .map(Gemstone::valueOf)
                        .collect(Collectors.toSet());
                int price = Integer.parseInt(recordFragments[PRICE_INDEX]);
                return new SaleRecord(date, preciousMetal, jewelry, gemstones, price);
            }).toList();
            logger.log(Level.INFO, "file read successfully");
            return records;
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "couldn't find the file, path given: " + path, e);
            System.out.println("oops! couldn't find the file\n");
            return Collections.emptyList();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "couldn't read from file, path given: " + path, e);
            System.out.println("oops! couldn't read from the file\n");
            return Collections.emptyList();
        }
    }

}
