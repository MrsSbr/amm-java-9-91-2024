package ru.vsu.amm.java;

public class Courier {
    private final String firstName;
    private final String lastName;
    private final int id;

    public Courier(String firstName, String lastName, int id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName=" + firstName + '\n' +
                "lastName=" + lastName + '\n' +
                "id=" + id;
    }
}