package ru.vsu.amm.java.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Deals {
    public static final int MANAGER_INDEX = 0;
    private final List<Deal> deals;
    private static final Logger logger = Logger.getLogger(Deals.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deals(String fileName) throws IOException, URISyntaxException {
        Path filePath = Path.of(Deals.class.getClassLoader().getResource(fileName).toURI());
        try (Stream<String> lines = Files.lines(filePath)) {
            deals = lines.map(Deals::parseDeal)
                    .toList();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading deals file: " + fileName, e);
            throw e;
        }
    }

    private static Deal parseDeal(String line) {
        try {
            String[] parts = line.split(";");
            return new Deal(
                    parts[MANAGER_INDEX],
                    parts[1],
                    Double.parseDouble(parts[2]),
                    LocalDate.parse(parts[3], FORMATTER)
            );
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Error parsing deal line: " + line, e);
            throw e;
        } catch (DateTimeParseException e) {
            logger.log(Level.SEVERE, "Error parsing time: " + line, e);
            throw e;
        }
    }

    public List<Deal> getDeals() {
        return deals;
    }
}