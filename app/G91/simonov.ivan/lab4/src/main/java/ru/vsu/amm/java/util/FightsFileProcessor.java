package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Fight;
import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FightsFileProcessor {

    public static void writeFights(List<Fight> fights, String fileName, boolean isAppend) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, isAppend))) {

            for (Fight fight : fights) {
                bufferedWriter.write(String.format("%d %s %s %s %s %s",
                        fight.tournamentNum(),
                        fight.date(),
                        fight.participant1(),
                        fight.participant2(),
                        fight.winner(),
                        fight.fatality()));
            }
        }
    }

    public static List<Fight> readFights(String fileName) {
        List<Fight> readData = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splits = line.split(" ");
                readData.add(new Fight(Integer.parseInt(splits[0]),
                        LocalDate.parse(splits[1]),
                        Hero.valueOf(splits[2]),
                        Hero.valueOf(splits[3]),
                        Hero.valueOf(splits[4]),
                        Fatality.valueOf(splits[5])
                ));
            }
        }
        catch (IOException e) {
            return new ArrayList<>();
        }
        return readData;
    }
}
