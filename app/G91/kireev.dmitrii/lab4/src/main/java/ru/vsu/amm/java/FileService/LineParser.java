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

        int suspectNameGroup = 1;
        int tortureInstrumentGroup = 2;
        int tortureTimeGroup = 3;
        int confessionObtainedGroup = 4;

        String suspectName = matcher.group(suspectNameGroup);
        TortureInstrument tortureInstrument = TortureInstrument.valueOf(matcher.group(tortureInstrumentGroup));
        Duration tortureTime = Duration.parse(matcher.group(tortureTimeGroup));
        boolean confessionObtained = Boolean.parseBoolean(matcher.group(confessionObtainedGroup));

        return new FileEntity(suspectName, tortureInstrument, tortureTime, confessionObtained);

    }

}
