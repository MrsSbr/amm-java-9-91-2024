package ru.vsu.amm.java.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deals {
    private final List<Deal> deals;
    private static final Logger logger = Logger.getLogger(Deals.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deals(String fileName) throws IOException, URISyntaxException {
        Path filePath = Path.of(Deals.class.getClassLoader().getResource(fileName).toURI());
        try {
            deals = Files.lines(filePath)
                    .map(Deals::parseDeal)
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
                    parts[0],
                    parts[1],
                    Double.parseDouble(parts[2]),
                    LocalDate.parse(parts[3], FORMATTER)
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing deal line: " + line, e);
            throw e;
        }
    }

    public List<Deal> getDeals() {
        return deals;
    }
}