package ru.vsu.amm.java.main.FileReader;

import ru.vsu.amm.java.main.Enum.KindOfSport;
import ru.vsu.amm.java.main.OlympicRecord.OlympicMedalsRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private final Pattern PATTERN = Pattern.compile("^([A-Za-z]+)," +
            "(SWIMMING|BOXING|RUNNING|HIGH_JUMP),([A-Za-z]+),(\\d)");

    public OlympicMedalsRecord parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line format");
        }

        final int countryGroup = 1;
        final int sportGroup = 2;
        final int athleteNameGroup = 3;
        final int placeGroup = 4;

        String countryName = matcher.group(countryGroup);
        KindOfSport sport = KindOfSport.valueOf(matcher.group(sportGroup));
        String athleteName = matcher.group(athleteNameGroup);
        Integer place = Integer.valueOf(matcher.group(placeGroup));

        return new OlympicMedalsRecord(countryName, sport, athleteName, place);

    }

}