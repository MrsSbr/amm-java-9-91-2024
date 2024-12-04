package ru.vsu.amm.java.Model;

import java.time.Duration;

public record FileEntity(
        String suspectName,
        TortureInstrument tortureInstrument,
        Duration tortureTime,
        boolean confessionObtained
) {
}
