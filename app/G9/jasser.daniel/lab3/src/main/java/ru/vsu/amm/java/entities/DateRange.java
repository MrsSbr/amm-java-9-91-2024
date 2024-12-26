package ru.vsu.amm.java.entities;

import java.time.LocalDate;

public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();
    }

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
