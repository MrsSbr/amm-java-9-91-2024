package ru.vsu.amm.java.Model;

import java.time.LocalDate;

public record Publication(
        String title,
        PublicationType type,
        LocalDate startDate,
        LocalDate endDate
) {
    // Метод для проверки, входит ли месяц в период подписки
    public boolean isInMonth(Month month) {
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();

        return (startMonth <= month.getNumber() && month.getNumber() <= endMonth);
    }
}