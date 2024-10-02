package ru.vsu.amm.java.winners;

import java.util.Objects;

public class Winner {
    private int year;
    private String name;
    private String departmentName;

    public Winner(int year, String name, String departmentName) {
        this.year = year;
        this.name = name;
        this.departmentName = departmentName;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNamee(String name) {
        this.name = name;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "year " + year +
                " name " + name +
                " departmentName " + departmentName + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Winner)) return false;
        Winner winner = (Winner) o;
        return year == winner.year && Objects.equals(name, winner.name) && Objects.equals(departmentName, winner.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, name, departmentName);
    }
}
