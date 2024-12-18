package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.BeautyBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileService {

    private static final Logger log;

    static {
        log = Logger.getLogger(FileService.class.getName());
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<BeautyBox> readBeautyBoxData(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            return reader.lines()
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public BeautyBox parseLine(String line) {
        String[] parts = line.split(";");
        LocalDate date = LocalDate.parse(parts[0], FORMATTER);
        int soldCount = Integer.parseInt(parts[1]);
        List<String> contents = Arrays.asList(parts[2].split(","));
        return new BeautyBox(date, soldCount, contents);
    }

}
