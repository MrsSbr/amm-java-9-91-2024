package ru.vsu.amm.java.FileService;

import ru.vsu.amm.java.Model.Subscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class FileReader {

    private final LineParser parser;
    private final Logger logger;

    public FileReader() {
        this.parser = new LineParser();
        this.logger = Logger.getLogger(FileReader.class.getName());
    }

    public List<Subscriber> read(String path) {

        List<Subscriber> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(path))) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                try {
                    result.add(parser.parse(line));

                } catch (IllegalArgumentException e) {

                    logger.severe(line + " - " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {

            logger.severe(path + " --- " + e.getMessage());
            return  new ArrayList<>();
        }

        return result;
    }
}
