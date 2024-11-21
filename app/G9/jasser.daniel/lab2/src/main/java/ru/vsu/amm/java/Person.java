package ru.vsu.amm.java;

import java.util.Objects;

public class Person {

    private final String firstName;
    private final String lastName;
    private final int age;
    private int immunity;
    private int health;
    private String disease;

    public Person(String firstName, String lastName, int age, int immunity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.immunity = immunity;
        this.health = 100;
        disease = "Absolutely healthy";
    }

    public int getImmunity() {
        if (health != 0) {
            return immunity;
        } else {
            immunity = -1;
            return immunity;
        }
    }

    public int getAge() {
        return age;
    }

    public void setDisease(String s) {
        disease = s;
    }

    public void conditionGettingWorse(int deteriorationNumber){
        health -= deteriorationNumber;
        if (health <= 0) {
            health = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && immunity == person.immunity && health == person.health
                && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, immunity, health);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", immunity=" + immunity +
                ", health=" + health +
                ", disease=" + disease + '\'' +
                '}';
    }
}
