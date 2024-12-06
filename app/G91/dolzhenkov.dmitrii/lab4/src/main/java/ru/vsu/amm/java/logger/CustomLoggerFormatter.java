package ru.vsu.amm.java.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLoggerFormatter extends Formatter {
    private static final String FORMAT = "%1$tF %1$tT %2$s%n%4$s: %5$s%6$s%n";

    @Override
    public String format(LogRecord record) {
        return String.format(FORMAT,
                new java.util.Date(record.getMillis()),
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getLevel().getLocalizedName(),
                record.getMessage(),
                record.getThrown() == null ? "" : "\n" + record.getThrown());
    }
}
