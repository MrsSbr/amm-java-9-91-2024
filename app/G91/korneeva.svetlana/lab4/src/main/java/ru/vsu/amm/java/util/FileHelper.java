package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.OlympiadRecord;
import ru.vsu.amm.java.enums.Subjects;

import java.io.*;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {
    private FileHelper(){
    }

    public static void generateFile(String path, int n) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < n; i++) {
                OlympiadRecord record = OlympiadRecordFactory.generateOlymiadRecord();
                writer.write(String.format(
                        "%d;%s;%d;%s%n",
                        record.year().getValue(),
                        record.subject().name(),
                        record.schoolClass(),
                        String.join(",", record.winners())
                ));
            }
        }
    }
    public static List<OlympiadRecord> getFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().map(line -> {
                String[] parts = line.split(";");
                Year year = Year.of(Integer.parseInt(parts[0]));
                Subjects subject = Subjects.valueOf(parts[1]);
                int schoolClass = Integer.parseInt(parts[2]);
                List<String> winners = List.of(parts[3].split(","));
                return new OlympiadRecord(year, subject, schoolClass, winners);
            }).collect(Collectors.toList());
        }
    }
}
