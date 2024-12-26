package ru.vsu.amm.java;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier courier)) return false;
        return id == courier.id && Objects.equals(firstName, courier.firstName) && Objects.equals(lastName, courier.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, id);
    }
}