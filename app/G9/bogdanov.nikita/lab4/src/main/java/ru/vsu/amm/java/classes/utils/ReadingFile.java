package ru.vsu.amm.java.classes.utils;

import ru.vsu.amm.java.classes.entity.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReadingFile {
    private static final Logger logger = Logger.getLogger(ReadingFile.class.getName());

    public static List<Employee> readEmployeeFromFile(String fileName) throws IOException {
        logger.info("Reading file: " + fileName);
        return Files.lines(Path.of(fileName))
                .map(line -> {
                    String[] parts = line.split(";");
                    return new Employee(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            Double.parseDouble(parts[2].trim())
                    );
                })
                .collect(Collectors.toList());
    }
}
