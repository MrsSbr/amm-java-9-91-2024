package ru.vsu.amm.java.classes.utils;

import ru.vsu.amm.java.classes.entity.Employee;
import ru.vsu.amm.java.classes.enums.Department;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadingFile {
    private static final Logger logger = Logger.getLogger(ReadingFile.class.getName());

    private static final int DEP_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int SALARY_INDEX = 2;

    public static List<Employee> readEmployeeFromFile(String fileName) throws IOException {
        logger.info("Reading file: " + fileName);
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            return lines
                    .map(line -> {
                        String[] parts = line.split(";");
                        return new Employee(
                                Department.valueOf(parts[DEP_INDEX].trim()),
                                parts[NAME_INDEX].trim(),
                                Double.parseDouble(parts[SALARY_INDEX].trim())
                        );
                    })
                    .toList();
        }
    }
}
