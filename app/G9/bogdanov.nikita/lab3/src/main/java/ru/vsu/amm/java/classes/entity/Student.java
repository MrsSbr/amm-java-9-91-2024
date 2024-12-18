package ru.vsu.amm.java.classes.entity;

import ru.vsu.amm.java.classes.enums.Gender;

import java.time.Month;

public class Student {
    private String name;
    private Gender gender;
    private Month monthOfBirth;

    public Student(String name, Gender gender, Month monthOfBirth) {
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

    public Month getMonthOfBirth() {
        return monthOfBirth;
    }
}
