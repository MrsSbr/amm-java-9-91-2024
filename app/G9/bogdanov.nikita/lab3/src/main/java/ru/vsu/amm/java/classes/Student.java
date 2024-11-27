package ru.vsu.amm.java.classes;

public class Student {
    private String name;
    private Gender gender;
    private int monthOfBirth;

    public Student(String name, Gender gender, int monthOfBirth) {
        this.name = name;
        this.gender = gender;
        this.monthOfBirth = monthOfBirth;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }
}
