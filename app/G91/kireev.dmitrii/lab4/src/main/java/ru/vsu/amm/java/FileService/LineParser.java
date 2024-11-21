package ru.vsu.amm.java.FileService;

import ru.vsu.amm.java.Model.FileEntity;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private final Pattern PATTERN = Pattern.compile("^([A-Za-z]+),(WATERBOARDING|WHIP|ELECTRIC_SHOCK|PHYSICAL_BEATING),(PT\\d+[HMSD]),(true|false)$");

    public FileEntity parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) throw new IllegalArgumentException("Invalid line format");


        String suspectName = matcher.group(1);
        TortureInstrument tortureInstrument = TortureInstrument.valueOf(matcher.group(2));
        Duration tortureTime = Duration.parse(matcher.group(3));
        boolean confessionObtained = Boolean.parseBoolean(matcher.group(4));

        return new FileEntity(suspectName, tortureInstrument, tortureTime, confessionObtained);

    }

}
