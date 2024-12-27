package ru.vsu.amm.java.winners;

import java.util.Objects;

public class PrizeRecipient implements RecipientInterface {
    private int cycle;
    private String name;
    private String teamName;


    public PrizeRecipient(int cycle, String name, String teamName) {
        this.cycle = cycle;
        this.name = name;
        this.teamName = teamName;
    }
    public int getYear() {
        return cycle;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDepartmentName() {
        return teamName;
    }
    public void setYear(int year) {
        this.cycle = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentName(String departmentName) {
        this.teamName = departmentName;
    }


    @Override
    public String toString() {
        return "cycle " + cycle +
                " name " + name +
                " teamName " + teamName + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrizeRecipient)) return false;
        PrizeRecipient that = (PrizeRecipient) o;
        return cycle == that.cycle && Objects.equals(name, that.name) && Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cycle, name, teamName);
    }
}