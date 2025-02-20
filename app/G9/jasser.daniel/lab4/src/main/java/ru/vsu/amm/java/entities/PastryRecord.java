package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enums.PastryName;

import java.time.LocalDate;

public class PastryRecord {

    private LocalDate completionDate;
    private PastryName pastryName;
    private int weight;
    private int cost;

    public PastryRecord(LocalDate completionDate, PastryName pastryName, int weight, int cost) {
        this.completionDate = completionDate;
        this.pastryName = pastryName;
        this.weight = weight;
        this.cost = cost;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public PastryName getPastryName() {
        return pastryName;
    }

    public void setPastryName(PastryName pastryName) {
        this.pastryName = pastryName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "PastryRecord{" +
                "completionDate=" + completionDate +
                ", pastryName=" + pastryName +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
