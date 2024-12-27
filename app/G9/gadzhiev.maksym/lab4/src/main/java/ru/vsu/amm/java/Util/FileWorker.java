package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Entity.Message;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FileWorker {

    private final static int DATE_INDEX = 0;
    private final static int FIO_INDEX = 1;
    private final static int TEXT_INDEX = 2;

    public FileWorker() {
    }

    public static void generateFile(String path, int n) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < n; ++i) {
                Message message = MessageFactory.generateMessage();

                String formattedDate = message.date().toString();
                bufferedWriter.write(String.format("%s;%s;%s%n", formattedDate, message.fio(), message.text()));
            }
        }
    }

    public static List<Message> getFromFile(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDate date = LocalDate.parse(parts[DATE_INDEX]);
                        String fio = parts[FIO_INDEX];
                        String text = parts[TEXT_INDEX];
                        return new Message(date, fio, text);
                    })
                    .toList();
        }
    }
}
