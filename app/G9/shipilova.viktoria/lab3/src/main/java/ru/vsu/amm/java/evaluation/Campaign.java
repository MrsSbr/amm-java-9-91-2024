package ru.vsu.amm.java.evaluation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Campaign {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int reach;
    private double budget;

    public Campaign(String type, LocalDate startDate, LocalDate endDate, int reach, double budget) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reach = reach;
        this.budget = budget;
    }

    public String getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getReach() {
        return reach;
    }

    public double getBudget() {
        return budget;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public long getDuration(){
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
