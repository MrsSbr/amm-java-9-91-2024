package ru.vsu.amm.java;

import java.util.Objects;

public class Student extends Person {
    private int course;

    public Student(String surname, String name, int course) {
        super(surname, name, Type.Learner);
        this.course = course;
    }

    public int getCourse() { return course; }

    @Override
    public void duty() { System.out.println(getSurname() + " " + getName() + " is studying");}

    @Override
    public String toString() {return super.toString() + ". Course: "+ course;}

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Student student = (Student) obj;
        return course == student.course;
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), course);}
}
