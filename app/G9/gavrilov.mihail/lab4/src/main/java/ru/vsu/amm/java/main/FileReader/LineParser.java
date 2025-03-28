package ru.vsu.amm.java.main.FileReader;

import ru.vsu.amm.java.main.Enum.KindOfSport;
import ru.vsu.amm.java.main.OlympicRecord.OlympicMedalsRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {
    final static int countryGroup = 1;
    final static int sportGroup = 2;
    final static int athleteNameGroup = 3;
    final static int placeGroup = 4;

    private final Pattern PATTERN = Pattern.compile("^([A-Za-z]+)," +
            "(SWIMMING|BOXING|RUNNING|HIGH_JUMP),([A-Za-z]+),(\\d)");

    public OlympicMedalsRecord parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String countryName = matcher.group(countryGroup);
        KindOfSport sport = KindOfSport.valueOf(matcher.group(sportGroup));
        String athleteName = matcher.group(athleteNameGroup);
        Integer place = Integer.valueOf(matcher.group(placeGroup));

        return new OlympicMedalsRecord(countryName, sport, athleteName, place);

    }

}