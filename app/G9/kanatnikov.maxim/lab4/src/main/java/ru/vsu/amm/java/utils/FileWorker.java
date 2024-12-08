package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entity.MarketingCampaign;
import ru.vsu.amm.java.enums.Type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWorker {
    public static final String WRITING_PATTERN = "%tF;%tF;%s;%d;%d";
    public static final String ERROR_READING_FILE = "Произошла ошибка при чтении данных из файла";
    public static final int DATE_START_INDEX = 0;
    public static final int DATE_END_INDEX = 1;
    public static final int TYPE_INDEX = 2;
    public static final int COVERAGE_INDEX = 3;
    public static final int BUDGET_INDEX = 4;
    private static final Logger LOGGER = Logger.getLogger(FileWorker.class.getName());
    private FileWorker() {
    }

    public static void write(List<MarketingCampaign> marketingCampaigns, String path) throws IOException {
        try (var bw = new BufferedWriter(new FileWriter(path))) {
            for (MarketingCampaign marketingCampaign : marketingCampaigns) {
                String line = String.format(WRITING_PATTERN,
                        marketingCampaign.dateStart(),
                        marketingCampaign.dateEnd(),
                        marketingCampaign.type(),
                        marketingCampaign.coverage(),
                        marketingCampaign.budget());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
            throw e;
        }
    }

    public static List<MarketingCampaign> read(String path) throws IOException {
        List<MarketingCampaign> marketingCampaigns = new ArrayList<>();

        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                try {
                    marketingCampaigns.add(new MarketingCampaign(LocalDate.parse(data[DATE_START_INDEX]),
                            LocalDate.parse(data[DATE_END_INDEX]),
                            Type.valueOf(data[TYPE_INDEX]),
                            Integer.parseInt(data[COVERAGE_INDEX]),
                            Integer.parseInt(data[BUDGET_INDEX])));
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    LOGGER.log(Level.SEVERE, ERROR_READING_FILE);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
            throw e;
        }

        return marketingCampaigns;
    }
}
