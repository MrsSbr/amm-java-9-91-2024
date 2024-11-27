package ru.vsu.amm.java.entities;

import java.util.Calendar;

public class DateRange {
    private Calendar startDate;
    private Calendar endDate;

    public DateRange() {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    public DateRange(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
}
