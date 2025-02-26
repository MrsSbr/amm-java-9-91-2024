package ru.vsu.amm.java;

public class Winner {
    private int year;

    private String name;

    private Department department;

    public Winner(int year, String name, Department department) {
        this.year = year;
        this.name = name;
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}