package ru.vsu.amm.java;

import java.time.LocalDate;

public record PublicationSubscription(
        String title,
        EditionType type,
        LocalDate startDate,
        LocalDate endDate
) {
    public boolean activeInMonth(ReportMonth month) {
        int start = startDate.getMonthValue();
        int end = endDate.getMonthValue();
        int m = month.getNumber();
        return start <= m && m <= end;
    }
}
