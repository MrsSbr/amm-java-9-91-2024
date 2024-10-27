package ru.vsu.amm.java.sports.medals;

import java.io.*;
import java.util.List;

public class DataHandler {
    public void saveToFale(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            List<Medal>  medals = MedalFactory.generateMedal();
            for (Medal medal : medals) {
                writer.write(String.format("%s;%s;%s;%d%n", medal.getCountry(), medal.getKindOfSport(), medal.getAthlete(), medal.getPlace()));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Medal> loadFromFale(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        Country country = Country.valueOf(parts[0]);
                        KindOfSport kindOfSport = KindOfSport.valueOf(parts[1]);
                        String athlete = parts[2];
                        int place = Integer.parseInt(parts[3]);
                        return new Medal(country, kindOfSport, athlete, place);
                    }).toList();
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
