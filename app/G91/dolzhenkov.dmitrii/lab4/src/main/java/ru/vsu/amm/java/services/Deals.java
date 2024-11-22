package ru.vsu.amm.java.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Deals {
    private final List<Deal> deals;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deals(String fileName) throws IOException, URISyntaxException {
        Path filePath = Path.of(Deals.class.getClassLoader().getResource(fileName).toURI());
        deals = Files.lines(filePath)
                .map(Deals::parseDeal)
                .toList();
    }

    private static Deal parseDeal(String line) {
        String[] parts = line.split(";");
        return new Deal(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                LocalDate.parse(parts[3], FORMATTER)
        );
    }

    public List<Deal> getDeals() {
        return deals;
    }
}