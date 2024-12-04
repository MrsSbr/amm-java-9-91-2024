package ru.vsu.amm.java;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkWithFile {

    private final Logger logger;

    public WorkWithFile(Logger logger) {
        this.logger = logger;
    }

    public List<Deal> read(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return bufferedReader.lines()
                    .map(line -> {
                        String[] words = line.split(",");
                        return new Deal(words[0], words[1], Double.parseDouble(words[2]), LocalDate.parse(words[3]));
                    })
                    .toList();
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return new ArrayList<>();
    }

    public void SaveToFile(String path, List<Deal> deals) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (var deal : deals) {
                bufferedWriter.write(deal.toString());
                bufferedWriter.write('\n');
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
